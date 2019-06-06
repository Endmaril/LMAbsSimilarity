# Language Model Based Similarity with Absolute Discount Smoothing
A language model based similarity with absolute discount smoothing for lucene 7.

This similarity is an implementation of what is discribed in https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.94.8019&rep=rep1&type=pdf.
Because of the restrictions of Lucene similarities, it had to be broken into two terms.
Indeed, Lucene similarities can store and use only one length normalization factor, must be positive and must decrease when the norm increases.

# The formula
The full similarity computation function looks like this:
log(1+(max(docrFreq-delta,0)) / (delta * d_u* p(w\c) ) ) + log( delta * d_u / |d|) 

d_u is the number of unique terms in the current document for this field
|d| is the number of terms in the document for this field
delta is a parameter (between 0 and 1)
p(w\c) is a language model computed on the corpus (we reuse here the work done in the LMSimilarity class from Lucene)
docFreq is the number of occurence of the searched term in the current document for this field

# First Term Similarity
The first term computed in LMAbsTerm1 is:
log(1+(max(docrFreq-delta,0)) / (delta * d_u* p(w\c) ) )

This log is always greater than or equal to 0 as the value of the expression inside is always greater than 1
The normalization factor saved is d_u, if d_u increases, the expression inside the log decreases, and so the log value decreases.
Lucene's restrictions are respected.

# Second Term Similarity
The second term computed in LMAbsTerm2 is:
-log( delta * d_u / |d|)

d_u / |d| is by definition <= 1, and delta is in [0, 1].
Therefore delta * d_u / |d| < 1 and thus log( delta * d_u / |d|) < 0 and -log( delta * d_u / |d|) > 0
The normalization factor is d_u / |d|, as it increases, the log increases and so -log decreases
Lucene's restrictions are respected.

# Compute The Final Value
To get the final value, one has to compute:
LMAbsTerm1 - LMAbsTerm2

This requires the user to define two field types.
Then duplicate the field on which one want to compute this value.
Then assign each of the field one of the similarities.
Performing the same query on both fields and gathering the scores.
Finally compute the final value.

# Additional Remarks
This is far from optimal and has been done to extract the value as a feature for machine learning purposes, not to be used as a ranking similarity directly.
Other way to implement the same kind of feature are available, such as building a custom Lucene query for exemple.
