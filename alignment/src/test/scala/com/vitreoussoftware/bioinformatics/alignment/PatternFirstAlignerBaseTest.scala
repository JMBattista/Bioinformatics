package com.vitreoussoftware.bioinformatics.alignment

import com.vitreoussoftware.test._
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.collection.JavaConversions._

/**
 * Set of Basic Behavior tests for TextFirstAlginers
 *
 * Created by John on 8/23/14.
 */
@RunWith(classOf[JUnitRunner])
abstract class PatternFirstAlignerBaseTest(anAligner: String) extends UnitSpec {
  val bases = List("A", "U", "C", "G")
  val baseSeqs = bases.map(base => BasicSequence.create(base, AcceptUnknownDnaEncodingScheme.instance).get())

  val seqSimple = BasicSequence.create(FastaStringFileStreamReaderTest.recordSimple, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord1 = BasicSequence.create(FastaStringFileStreamReaderTest.record1, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord2 = BasicSequence.create(FastaStringFileStreamReaderTest.record2, AcceptUnknownDnaEncodingScheme.instance).get()
  val seqRecord3 = BasicSequence.create(FastaStringFileStreamReaderTest.record3, AcceptUnknownDnaEncodingScheme.instance).get()

  val sourceSeqs = List(seqSimple, seqRecord1, seqRecord2, seqRecord3)

  /**
   * Perform any setup necessary to create and return the aligner for use in tests
   * @return the aligner to use for the tests
   */
  def getAligner() : PatternFirstAligner

  /**
   * Perform any cleanup necessary to dispose of the aligner.
   * By default no cleanup is performed
   * @param aligner the aligner used by the test
   * @return None
   */
  def destroyAligner(aligner: PatternFirstAligner) = None

  def withAligner(test: (PatternFirstAligner) => Unit) = {
    val aligner = getAligner()
    test(aligner)
    destroyAligner(aligner)
  }

  "An empty " + anAligner should "should not contain any alignments" in {
    withAligner {
      (aligner) => {
        baseSeqs.map(pattern => (pattern.toString, aligner.addPattern(pattern)))

        val results = aligner.containedIn(seqSimple)
        results should contain theSameElementsAs baseSeqs
      }
    }
  }

  it should "should not provide any positions" in {
    withAligner {
      (aligner) => {
        val results = baseSeqs.map(x => (x.toString, aligner.getAlignments(x).size()))
        val expected = bases.zip(List.fill(bases.length)(0))
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
        aligner.addPattern(BasicSequence.create(FastaStringFileStreamReaderTest.recordSimple, AcceptUnknownDnaEncodingScheme.instance).get())
      }
    }
  }

  anAligner + "built with the SimpleRecord" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.containedIn(x)))
        val expected = bases.zip(List.fill(bases.length)(true))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "provide positions" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.getAlignments(x).size()))
        val expected = bases.zip(List(22, 20, 13, 25))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "determine shortest values for single base sequences" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.shortestDistance(x).size()))
        val expected = bases.zip(List.fill(bases.length)(1))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "determine distances for single base sequences" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqSimple)
        val results = baseSeqs.map(x => (x.toString, aligner.distances(x).size()))
        val expected = bases.zip(List.fill(bases.length)(1))
        results should contain theSameElementsInOrderAs expected
      }
    }
  }

  it should "allow adding a sequence" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqSimple)
        aligner.addPattern(seqRecord1)
      }
    }
  }

  it should "find its identity pattern" in {
    withAligner(
      (aligner) => {
        aligner.addPattern(seqSimple)
        aligner.containedIn(seqSimple) should contain theSameElementsAs  baseSeqs
        aligner.getAlignments(seqSimple) should contain theSameElementsAs  (List(Alignment.`with`(seqSimple, 0)))
      })
  }

  it should "not contain the record1 pattern" in {
    withAligner(
      (aligner) => {
        aligner.addPattern(seqSimple)
        aligner.containedIn(seqRecord1) should contain theSameElementsAs  baseSeqs
        aligner.getAlignments(seqRecord1) should contain theSameElementsAs  (List())
      }
    )
  }

  anAligner + "built with the Record1" should "contain the single base patterns" in {
    withAligner {
      (aligner) => {
        aligner.addPattern(seqRecord1)
        baseSeqs.map(x => (x.toString, aligner.containedIn(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))
      }
    }
  }

  anAligner + "built with recordSimple/1/2/3" should "be able to report matches across all the parents" in {
    withAligner(
      (aligner) => {
        sourceSeqs.map(text => aligner.addPattern(text))

        // All the bases are contained in this alligner
        baseSeqs.map(x => (x.toString, aligner.containedIn(x))) should contain theSameElementsInOrderAs
          bases.zip(List.fill(bases.length)(true))

        val parentSets = baseSeqs.map(pattern => (pattern.toString, aligner.getAlignments(pattern).toList.map(p => p.getSequence).toSet))

        forAll (parentSets) {
          set => set._2 should contain theSameElementsAs  sourceSeqs
        }
      }
    )
  }
}
