package com.vitreoussoftware.bioinformatics.alignment

import com.vitreoussoftware.test._
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest

/**
 * Set of Basic Behavior tests for TextFirstAlginers
 *
 * Created by John on 8/23/14.
 */
abstract class TextFirstAlignerBaseTest(aligner: String) extends UnitSpec {
  val baseSeqs = List(BasicSequence.create("A", AcceptUnknownDnaEncodingScheme.instance).get(),
                      BasicSequence.create("T", AcceptUnknownDnaEncodingScheme.instance).get(),
                      BasicSequence.create("C", AcceptUnknownDnaEncodingScheme.instance).get(),
                      BasicSequence.create("G", AcceptUnknownDnaEncodingScheme.instance).get(),
                      BasicSequence.create("N", AcceptUnknownDnaEncodingScheme.instance).get())

  def getAligner() : TextFirstAligner

  "An aligner that has not been initialized" should " not contain any patterns" in {
    val aligner = getAligner()

    val results = baseSeqs.map(x => (x.toString, aligner.contains(x)))

    assert(results.find(x => x._2 == true).isEmpty == true)
  }

  it should "not provide any positions" in {
    val aligner = getAligner()

    val results = baseSeqs.map(x => (x.toString, aligner.getPositions(x)))

    assert(results.find(x => x._2 == true).isEmpty == true)
  }

  it should "not provide any shortest distance values " in {
    val aligner = getAligner()

    val results = baseSeqs.map(x => (x.toString, aligner.distance(x).size()))

    assert(results.find(x => x._2 != 0).isEmpty == true)
  }

  it should "not provide any distance values " in {
    val aligner = getAligner()

    val results = baseSeqs.map(x => (x.toString, aligner.distances(x).size()))

    assert(results.find(x => x._2 != 0).isEmpty == true)
  }

  it should "allow adding a sequence" in {
    val aligner = getAligner()

    aligner.addSequence(BasicSequence.create(FastaStringFileStreamReaderTest.recordSimple, AcceptUnknownDnaEncodingScheme.instance).get())
  }
}
