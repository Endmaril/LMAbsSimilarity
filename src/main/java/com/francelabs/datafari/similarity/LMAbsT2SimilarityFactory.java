/**
 * Copyright 2019 FranceLabs

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 */
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