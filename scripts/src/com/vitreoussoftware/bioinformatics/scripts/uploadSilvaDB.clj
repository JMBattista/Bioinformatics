(ns com.vitreoussoftware.bioinformatics.scripts.core
  (:import (com.vitreoussoftware.bioinformatics.sequence.reader.fasta SequenceFromFastaStringStreamReader
                                                                      FastaStringFileStreamReader)
           (com.vitreoussoftware.bioinformatics.sequence.collection.mongodb MongoDBSequenceCollection
                                                                            MongoDBSequenceCollectionFactory)
           (com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory)
           [com.vitreoussoftware.bioinformatics.sequence.fasta FastaSequenceFactory]))

(defn readSequenceStrings [fileName]
  (seq (FastaStringFileStreamReader/create fileName)))

(defn readSequences [fileName]
  (seq (new SequenceFromFastaStringStreamReader (FastaStringFileStreamReader/create fileName))))

(def stringReader (readSequenceStrings "c:/development/data/bioinformatics/silvadb/SSUParc_115.fasta"))
(def sequenceReader (readSequences "c:/development/data/bioinformatics/silvadb/SSUParc_115.fasta"))
(def encoder (new FastaSequenceFactory))

(def mongoDbCollection (.getSequenceCollection (new MongoDBSequenceCollectionFactory "localhost", 27017, "SilvaDB", "SSUParc_115")))

(def badCount (atom 0))

(defn incIfFalse [f a]
  (cond
    (eval f) true
    :else (do
            (swap! a inc)
            false)))


(.addAll mongoDbCollection ;; Add them all in one go, but start uploading immediately
  (map #(.get %) (filter #(incIfFalse (.isPresent %) badCount) sequenceReader)))


(println "Number of failed sequences " @badCount)
