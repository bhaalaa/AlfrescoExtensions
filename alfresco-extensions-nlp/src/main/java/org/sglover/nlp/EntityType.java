/*
 * Copyright 2015 Alfresco Software, Ltd.  All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd. 
 * pursuant to a written agreement and any use of this program without such an 
 * agreement is prohibited. 
 */
package org.sglover.nlp;

/**
 * 
 * @author sglover
 *
 */
public enum EntityType
{
    names()
    {
        public String getName()
        {
            return "names";
        }
    },

    times()
    {
        public String getName()
        {
            return "times";
        }
    },

    durations()
    {
        public String getName()
        {
            return "durations";
        }
    },

    numbers()
    {
        public String getName()
        {
            return "numbers";
        }
    },

    locations()
    {
        public String getName()
        {
            return "locations";
        }
    },

    orgs()
    {
        public String getName()
        {
            return "orgs";
        }
    },

    dates()
    {
        public String getName()
        {
            return "dates";
        }
    },

    misc()
    {
        public String getName()
        {
            return "misc";
        }
    },

    money()
    {
        public String getName()
        {
            return "money";
        }
    };

    public abstract String getName();
}
