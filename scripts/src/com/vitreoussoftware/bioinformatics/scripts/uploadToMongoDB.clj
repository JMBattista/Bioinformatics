(ns com.vitreoussoftware.bioinformatics.scripts
  (:import (com.vitreoussoftware.bioinformatics.sequence.reader.fasta SequenceFromFastaStringStreamReader
                                                                      FastaStringFileStreamReader)
           (com.vitreoussoftware.bioinformatics.sequence.collection.mongodb MongoDBSequenceCollection
                                                                            MongoDBSequenceCollectionFactory)))

(defn readSequenceStrings [fileName]
  (seq (FastaStringFileStreamReader/create fileName)))

(defn readSequences [fileName]
  (seq (new SequenceFromFastaStringStreamReader (FastaStringFileStreamReader/create fileName))))

(def sequenceReader (readSequences "c:/development/data/bioinformatics/silvadb/SSUParc_115.fasta"))

(def mongoDbCollection (.getSequenceCollection (new MongoDBSequenceCollectionFactory "localhost", 27017, "SilvaDB", "textX"))) ;; SSUParc_115

(defn incIfFalse [f a]
  (cond
    (eval f) true
    :else (do
            (swap! a inc)
            false)))


(defn uploadToMongoDB [mongoDbCollection, collection]
  (def badCount (atom 0))
  (.addAll mongoDbCollection ;; Add them all in one go, but start uploading immediately
    (map #(.get %) (filter #(incIfFalse (.isPresent %) badCount) sequenceReader)))
  {:size (.size mongoDbCollection), :fail @badCount}
 )

(uploadToMongoDB mongoDbCollection sequenceReader)

(println "Finished uploading!")
(println "Number of failed sequences " @badCount)