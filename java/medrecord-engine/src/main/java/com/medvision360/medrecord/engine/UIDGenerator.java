package com.medvision360.medrecord.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.medvision360.medrecord.spi.LocatableTransformer;
import com.medvision360.medrecord.spi.UIDFactory;
import com.medvision360.medrecord.api.exceptions.TransformException;
import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.composition.Composition;
import org.openehr.rm.composition.content.entry.Instruction;
import org.openehr.rm.composition.content.entry.Observation;
import org.openehr.rm.composition.content.navigation.Section;
import org.openehr.rm.support.identification.UIDBasedID;

public class UIDGenerator extends UIDFactory implements LocatableTransformer
{
    @Override
    public void transform(Locatable locatable) throws TransformException
    {
        if (locatable == null)
        {
            return;
        }
        
        UIDBasedID uid = locatable.getUid();
        if (uid == null)
        {
            uid = makeUID();
            setUid(locatable, uid);
        }
        
        if (locatable instanceof Composition)
        {
            transformAll(((Composition) locatable).getContent());
        }
        else if (locatable instanceof Section)
        {
            transformAll(((Section) locatable).getItems());
        }
        else if (locatable instanceof Instruction)
        {
            transformAll(((Instruction) locatable).getActivities());
        }
        else if (locatable instanceof Observation)
        {
            transform(((Observation) locatable).getData());
            transform(((Observation) locatable).getState());
        }
//        else if (locatable instanceof ItemList)
//        {
//            ItemList itemList = (ItemList) locatable;
//            List<Element> elements = itemList.getItems();
//            for (Element element : elements)
//            {
//                transform(element);
//            }
//        }
        
    }

    private void transformAll(Iterable<? extends Locatable> all) throws TransformException
    {
        if (all == null)
        {
            return;
        }
        
        for (Locatable child : all)
        {
            transform(child); // recurse!
        }
    }

    protected void setUid(Locatable locatable, UIDBasedID uid) throws TransformException
    {
        try
        {
            Method[] methods = Locatable.class.getDeclaredMethods();
            Method setter = null;
            for (Method method : methods)
            {
                if ("setUid".equals(method.getName()))
                {
                    setter = method;
                    break;
                }
            }
            if (setter == null)
            {
                throw new NoSuchMethodException("setUid");
            }
            setter.setAccessible(true);
            setter.invoke(locatable, uid);
        }
        catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e)
        {
            throw new TransformException(e);
        }
    }

}
