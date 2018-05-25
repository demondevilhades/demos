package test.nlp.common;

import java.util.Collection;

import test.nlp.dict.pos.POS;
import test.nlp.region.Region;

public class LocationTerm extends Term
{

    private Collection<Region> regions;

    public LocationTerm(int offset, String word)
    {
        super(offset, word, POS.ns, 1);
    }

    public LocationTerm(int offset, String word, POS pos, int freq)
    {
        super(offset, word, pos, freq);
    }

    public Collection<Region> getRegions()
    {
        return regions;
    }

    public void setRegions(Collection<Region> regions)
    {
        this.regions = regions;
    }
}
