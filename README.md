#Bioinformatics

*A series of Java compatible libraries for performing basic Bioinformatics tasks.*

## Language
Most libraries today exist for Python with a heavy reliance on C code to get around issues with the GIL. While C is extremely performant, it is not a beginner friendly language, hence the reliance on Python wrappers for use by the scientific community.

## Usage
My experience with a number of these libraries is that they are chained together using the | character on *nix systems. While this is a very powerful technique for supporting emergent use cases, it is hampered by performance issues. Namely the | passes everything as text so the data must be serialized out, then deserialized back in. Creating libraries on the JVM would allow them to be hooked together by some simple glue code w/o the overhead of serialization / deserialization. 

## Motivation
I've been working on these libraries to learn more about the Data Structures and techniques used in Bioinformatics, as well as a non-trivial test bed for exploring software design.

Enjoy!

[ ![Codeship Status for JMBattista/Bioinformatics](https://codeship.com/projects/e2907780-3313-0132-a6d8-3a623e75de45/status?branch=master)](https://codeship.com/projects/40553)
[![codecov](https://codecov.io/gh/JMBattista/Bioinformatics/branch/master/graph/badge.svg)](https://codecov.io/gh/JMBattista/Bioinformatics)

