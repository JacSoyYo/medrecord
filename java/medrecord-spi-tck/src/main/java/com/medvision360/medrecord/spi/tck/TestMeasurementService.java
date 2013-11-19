/*
 * component:   "openEHR Reference Implementation"
 * description: "Class TestMeasurementService"
 * keywords:    "unit test"
 *
 * author:      "Rong Chen <rong@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2004 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/BRANCHES/RM-1.0-update/libraries/src/test/org/openehr/rm/support/measurement/TestMeasurementService.java $"
 * revision:    "$LastChangedRevision: 2 $"
 * last_change: "$LastChangedDate: 2005-10-12 23:20:08 +0200 (Wed, 12 Oct 2005) $"
 */
package com.medvision360.medrecord.spi.tck;

import org.openehr.rm.support.measurement.MeasurementService;

/**
 * Implementation of MeasurementService used for testing
 *
 * @author Rong Chen
 * @version 1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class TestMeasurementService implements MeasurementService
{
    private static final long serialVersionUID = 0x130L;

    public boolean isValidUnitsString(String units)
    {
        return true;
    }

    public boolean unitsEquivalent(String units1, String units2)
    {
        return true;
    }

    public static MeasurementService getInstance()
    {
        return new TestMeasurementService();
    }

    @Override
    public boolean unitsComparable(String units1, String units2)
    {
        return true;
    }

    @Override
    public int compare(String units1, Double value1, String units2,
            Double value2)
    {
        return 0;
    }
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is TestMeasurementService.java
 *
 *  The Initial Developer of the Original Code is Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */