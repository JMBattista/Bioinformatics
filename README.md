Bioinformatics

A series of Java compatible libaries for performing basic Bioinformatics tasks. Most libraries today exist for Python with a heavy reliance on C code to get around issues with the GIL. While C can be extremely performant it is also difficult to work with due to the lack of garbage collection. Another issue I with many of these libraries lies in how multiple such libraries are used in conjuction. My experience so far is that going between two different libraries generally results in writing the contents out in string form and then re-importing it into the next application, largely via bash piping. While this is a very powerful technique in its simplicity it can have enourmous overhead as the data size grows. 

I've started constructing these Libraries in Java for my own edification with respect to the techniques and data structures used for Bioinformatics programming and to start building a baseline for working Bioinformatics with a heavier emphasis on abstractions.

[ ![Codeship Status for JMBattista/Bioinformatics](https://codeship.io/projects/e2907780-3313-0132-a6d8-3a623e75de45/status)](https://codeship.io/projects/40553)
