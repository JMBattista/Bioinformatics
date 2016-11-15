package com.vitreoussoftware.bioinformatics.alignment

import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import org.junit.runner.RunWith
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.Matchers
import org.scalatest.junit.JUnitRunner

import scala.collection.JavaConversions._
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection
import com.vitreoussoftware.bioinformatics.sequence.encoding.ExpandedIupacEncodingScheme
import org.scalatest.{Inside, Matchers}
import com.vitreoussoftware.bioinformatics.sequence.{BasePair, Sequence}
import org.scalacheck.{Arbitrary, Gen}
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData
import com.vitreoussoftware.test.UnitSpec

trait AlignerTestData {
  val bases = List("A", "U", "C", "G")
  val baseSeqs = seqsFrom(bases)
  val offByNSeqs = seqsFrom(List("AAN", "ANA", "NAA"))
  val offByNSeqsLong = seqsFrom(List("AAAAAAAAAAN", "AAAAANAAAAA", "NAAAAAAAAAAA"))
  val testData = new FastaData();

  val seqSimple = testData.getSimpleSequence();
  val seqRecord1 = testData.getRealExample1Sequence();
  val seqRecord2 = testData.getRealExample2Sequence();
  val seqRecord3 = testData.getRealExample3Sequence();

  val sourceSeqs = List(seqSimple, seqRecord1, seqRecord2, seqRecord3)

  def seqsFrom(seqs: List[String]) = {
    seqs.map(seq => BasicSequence.create(seq, ExpandedIupacEncodingScheme.instance).get())
  }

  def zip[T1, T2](list: List[T1], value: T2) = {
    list.zip(List.fill(list.length)(value))
  }

  def checkDistance(alignment: Alignment) = {
    val pattern = alignment.getPattern
    alignment.getText.drop(alignment.getPosition).take(pattern.length()).zip(pattern)
      .map(value => value._1 distance value._2).sum
  }

  def genSequence(): Gen[Sequence] = {
    val genBasePair = for (bp <- Gen.frequency(
      (1, "A"),
      (1, "U"),
      (1, "C"),
      (1, "G")
    )) yield bp

    def makeSeq(seq: List[String]) = {
      var builder = new StringBuilder
      seq.foreach(bp => builder = builder append bp)
      BasicSequence.create(builder.toString(), ExpandedIupacEncodingScheme.instance).get()
    }

    def genSeq(len: Int): Gen[Sequence] = {
      for {seq <- Gen.listOfN(len, genBasePair)} yield makeSeq(seq)
    }

    def sizedSequence(len: Int) = {
      var length = len % 50
      if (len < 0)
        length = -1 * len
      if (length == 0)
        length = 1
      genSeq(length)
    }

    Gen.sized(sz => sizedSequence(sz))
  }

  val genBasePair = for (bp <- Gen.frequency(
    (1, 'A'),
    (1, 'U'),
    (1, 'C'),
    (1, 'G')
  )) yield BasePair.create(bp, ExpandedIupacEncodingScheme.instance)

  implicit lazy val arbBasePair: Arbitrary[BasePair] = Arbitrary(genBasePair)
  implicit lazy val arbSequence: Arbitrary[Sequence] = Arbitrary(genSequence())
}

trait AlignerHelpers extends Matchers {
  /**
    * Perform any setup necessary to fromCharacter and return the aligner for use in tests
    *
    * @return the aligner to use for the tests
    */
  def getAligner(): TextFirstAligner

  /**
    * Perform any cleanup necessary to dispose of the aligner.
    * By default no cleanup is performed
    *
    * @param aligner the aligner used by the test
    * @return None
    */
  def destroyAligner(aligner: TextFirstAligner) = None
}

/**
  * Set of Basic Behavior tests for TextFirstAlginers
  *
  * Created by John on 8/23/14.
  */
@RunWith(classOf[JUnitRunner])
abstract class TextFirstAlignerBaseTest(anAligner: String) extends UnitSpec with AlignerTestData with AlignerHelpers {
  /**
    * Perform the test with an aligner
    *
    * @param test the test to run
    */
  def withAligner(test: (TextFirstAligner) => Unit) = {
    val aligner = getAligner()
    test(aligner)
    destroyAligner(aligner)
  }


  "An empty " + anAligner should "should not contain any patterns" in {
    withAligner {
      (aligner) => {
        forAll(baseSeqs) { (seq) => {
          aligner contains seq shouldBe false
        }
        }
      }
    }
  }

  it should "should not provide any positions" in {
    withAligner {
      (aligner) => {
        val results = baseSeqs.map(x => (x.toString, aligner.getAlignments(x).size()))
        val expected = zip(bases, 0)
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "should not provide any shortest shortestDistance values" in {
    withAligner {
      (aligner) => {
        val results = baseSeqs.map(x => (x.toString, aligner.shortestDistance(x).size()))
        val expected = bases.zip(List.fill(bases.length)(0))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "should not provide any shortestDistance values " in {
    withAligner {
      (aligner) => {
        val results = baseSeqs.map(x => (x.toString, aligner.distances(x).size()))
        val expected = bases.zip(List.fill(bases.length)(0))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "should allow adding a sequence" in {
    withAligner {
      (aligner) => {
        aligner.addText(testData.getSimpleSequence)
      }
    }
  }

  anAligner + "built with the SimpleRecord" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addText(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.contains(x)))
        val expected = bases.zip(List.fill(bases.length)(true))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "report zero as the shortest distance for all single base pairs" in {
    withAligner {
      (aligner) => {
        aligner.addText(seqSimple)
        val results = baseSeqs.map(x => (x, aligner.shortestDistance(x)))

        results should have size (baseSeqs.size)

        for ((pattern, alignments) <- results) {
          pattern should have length 1
          val patternBase = pattern.get(0);
          alignments.size should be(seqSimple.filter(bp => bp == patternBase) size)

          forAll(alignments) { alignment =>
            alignment.getText should be(seqSimple)
            alignment.getDistance should be(0)
          }
        }
      }
    }
  }

  it should "also work for a collection of base sequences" in {
    withAligner {
      (aligner) => {
        aligner.addText(seqSimple)
        val results = aligner.shortestDistance(SequenceCollection.from(baseSeqs)).map(pair => (pair.getValue0, pair.getValue1))

        results should have size (baseSeqs.size)

        for ((pattern, alignments) <- results) {
          pattern should have length 1
          val patternBase = pattern.get(0);
          alignments.size should be(seqSimple.filter(bp => bp == patternBase) size)

          forAll(alignments) { alignment =>
            alignment.getText should be(seqSimple)
            alignment.getDistance should be(0)
          }
        }

      }
    }
  }

  it should "allow adding a sequence" in {
    withAligner {
      (aligner) => {
        aligner.addText(seqSimple)
        aligner.addText(seqRecord1)
      }
    }
  }

  it should "not contain the record1 pattern" in {
    withAligner(
      (aligner) => {
        aligner.addText(seqSimple)
        aligner.contains(seqRecord1) should be(false)
        aligner.getAlignments(seqRecord1) should contain theSameElementsAs (List())
      }
    )
  }

  anAligner + "built with the Record1" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addText(seqRecord1)
        baseSeqs.map(x => (x.toString, aligner.contains(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))
      }
    }
  }

  anAligner + "built with recordSimple/1/2/3" should "be able to report matches across all the parents" in {
    withAligner(
      (aligner) => {
        sourceSeqs.map(text => aligner.addText(text))

        // All the bases are contained in this alligner
        baseSeqs.map(x => (x.toString, aligner.contains(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))

        val parentSets = baseSeqs.map(pattern => (pattern.toString, aligner.getAlignments(pattern).toList.map(p => p.getText).toSet))

        forAll(parentSets) {
          set => set._2 should contain theSameElementsAs sourceSeqs
        }
      }
    )
  }
}
