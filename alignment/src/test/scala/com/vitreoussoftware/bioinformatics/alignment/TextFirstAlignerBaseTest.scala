package com.vitreoussoftware.bioinformatics.alignment

import com.vitreoussoftware.test._
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.JavaConversions._
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection
import org.scalatest.{Inside, Matchers}

trait AlignerTestData {
  val bases = List("A", "U", "C", "G")
  val baseSeqs = seqsFrom(bases)
  val offByNSeqs = seqsFrom(List("AAN", "ANA", "NAA"))
  val offByNSeqsLong = seqsFrom(List("AAAAAAAAAAN", "AAAAANAAAAA", "NAAAAAAAAAAA"))

  val seqSimple = BasicSequence.create(FastaStringFileStreamReaderTest.recordSimple, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord1 = BasicSequence.create(FastaStringFileStreamReaderTest.record1, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord2 = BasicSequence.create(FastaStringFileStreamReaderTest.record2, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord3 = BasicSequence.create(FastaStringFileStreamReaderTest.record3, AcceptUnknownDnaEncodingScheme.instance).get()

  val sourceSeqs = List(seqSimple, seqRecord1, seqRecord2, seqRecord3)

  def seqsFrom(seqs: List[String]) = {
    seqs.map(seq => BasicSequence.create(seq, AcceptUnknownDnaEncodingScheme.instance).get())
  }

  def zip[T1, T2](list: List[T1], value: T2) = {
    list.zip(List.fill(list.length)(value))
  }
}

trait AlignerHelpers extends Matchers {
  /**
   * Perform any setup necessary to create and return the aligner for use in tests
   * @return the aligner to use for the tests
   */
  def getAligner() : TextFirstAligner

  /**
   * Perform any cleanup necessary to dispose of the aligner.
   * By default no cleanup is performed
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
        aligner.addSequence(BasicSequence.create(FastaStringFileStreamReaderTest.recordSimple, AcceptUnknownDnaEncodingScheme.instance).get())
      }
    }
  }

  anAligner + "built with the SimpleRecord" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.contains(x)))
        val expected = bases.zip(List.fill(bases.length)(true))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "provide positions" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.getAlignments(x).size()))
        val expected = bases.zip(List(22, 20, 13, 25))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "report zero as the shortest distance for all single base pairs" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        val results = baseSeqs.map(x => (x, aligner.shortestDistance(x)))

        results should have size(baseSeqs.size)

        for ((pattern, alignments) <- results) {
          pattern should have length 1
          val patternBase = pattern.get(0);
          alignments.size should be(seqSimple.filter(bp => bp == patternBase) size)

          forAll(alignments) { alignment =>
            alignment.getSequence should be(seqSimple)
            alignment.getDistance should be(0)
          }
        }
      }
    }
  }

  it should "also work for a collection of base sequences" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        val results = aligner.shortestDistance(SequenceCollection.from(baseSeqs)).map(pair => (pair.getValue0, pair.getValue1))

        results should have size(baseSeqs.size)

        for ((pattern, alignments) <- results) {
          pattern should have length 1
          val patternBase = pattern.get(0);
          alignments.size should be(seqSimple.filter(bp => bp == patternBase) size)

          forAll(alignments) { alignment =>
            alignment.getSequence should be(seqSimple)
            alignment.getDistance should be(0)
          }
        }

      }
    }
  }

  it should "determine distances for single base sequences" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.distances(x).size()))
        val expected = bases.zip(List.fill(bases.length)(1))
       results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "allow adding a sequence" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqSimple)
        aligner.addSequence(seqRecord1)
      }
    }
  }

  it should "find its identity pattern" in {
    withAligner(
      (aligner) => {
        aligner.addSequence(seqSimple)
        aligner.contains(seqSimple) should be (true)
        aligner.getAlignments(seqSimple) should contain theSameElementsAs  (List(Alignment.`with`(seqSimple, 0)))
      })
  }

  it should "not contain the record1 pattern" in {
    withAligner(
      (aligner) => {
        aligner.addSequence(seqSimple)
        aligner.contains(seqRecord1) should be (false)
        aligner.getAlignments(seqRecord1) should contain theSameElementsAs  (List())
      }
    )
  }

  anAligner + "built with the Record1" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addSequence(seqRecord1)
        baseSeqs.map(x => (x.toString, aligner.contains(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))
      }
    }
  }

  anAligner + "built with recordSimple/1/2/3" should "be able to report matches across all the parents" in {
    withAligner(
      (aligner) => {
        sourceSeqs.map(text => aligner.addSequence(text))

        // All the bases are contained in this alligner
        baseSeqs.map(x => (x.toString, aligner.contains(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))

        val parentSets = baseSeqs.map(pattern => (pattern.toString, aligner.getAlignments(pattern).toList.map(p => p.getSequence).toSet))

        forAll (parentSets) {
          set => set._2 should contain theSameElementsAs  sourceSeqs
        }
      }
    )
  }
}
