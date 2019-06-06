package com.francelabs.datafari.similarity;

import org.apache.lucene.search.similarities.Similarity;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.schema.SimilarityFactory;

/**
 *
 */

public class LMAbsT2SimilarityFactory extends SimilarityFactory {
  private float delta;

  @Override
  public void init(SolrParams params) {
    super.init(params);
    delta = params.getFloat("delta", 0.7f);
  }

  @Override
  public Similarity getSimilarity() {
    LMAbsSimilarityT2 sim = new LMAbsSimilarityT2(delta);
    return sim;
  }
}