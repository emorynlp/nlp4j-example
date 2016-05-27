/**
 * Copyright 2016, Emory University
 * 
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
package edu.emory.mathcs.nlp.demo;

import edu.emory.mathcs.nlp.common.util.IOUtils;
import edu.emory.mathcs.nlp.common.util.Joiner;
import edu.emory.mathcs.nlp.component.template.NLPComponent;
import edu.emory.mathcs.nlp.component.template.config.NLPConfig;
import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.component.template.reader.NLPReader;
import edu.emory.mathcs.nlp.component.template.reader.TSVReader;
import edu.emory.mathcs.nlp.decode.NLPUtils;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class DEPDecode
{
	public static void main(String[] args) throws Exception
	{
		final String configurationFile = "src/main/resources/configuration/config-decode-dep.xml";
		final String inputFile = "src/main/resources/dat/sample-dev.tsv";
		final String modelFile = "edu/emory/mathcs/nlp/models/en-dep.xz";
		
		NLPComponent<NLPNode> parser = NLPUtils.getComponent(IOUtils.getInputStream(modelFile));
		NLPConfig<NLPNode> config = new NLPConfig<>(IOUtils.createFileInputStream(configurationFile));
		TSVReader<NLPNode> reader = new NLPReader(config.getReaderFieldMap());
		NLPNode[] nodes;
		
		// this is not necessary if these fields were not specified in the configuration file
		reader.deprel = reader.dhead = -1;
		reader.open(IOUtils.createFileInputStream(inputFile));
		
		while ((nodes = reader.next()) != null)
		{
			parser.process(nodes);
			System.out.println(Joiner.join(nodes, "\n", 1)+"\n");
		}
		
		reader.close();
	}
}
