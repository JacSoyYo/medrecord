package com.medvision360.medrecord.server.query;

import org.restlet.representation.Representation;

import com.medvision360.medrecord.api.exceptions.IORecordException;
import com.medvision360.medrecord.api.exceptions.InvalidQueryException;
import com.medvision360.medrecord.api.exceptions.RecordException;
import com.medvision360.medrecord.api.exceptions.UnsupportedQueryException;
import com.medvision360.medrecord.api.query.AQLQueryResource;
import com.medvision360.medrecord.server.AbstractServerResource;

public class AQLQueryServerResource extends AbstractServerResource implements AQLQueryResource {

	@Override
	public Representation aqlQuery() throws InvalidQueryException,
			UnsupportedQueryException, RecordException, IORecordException {
		// TODO Auto-generated method stub
		return null;
	}

}
