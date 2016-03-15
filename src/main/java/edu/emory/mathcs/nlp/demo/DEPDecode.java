/**
 * Copyright 2015, Emory University
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

import java.io.InputStream;
import java.io.ObjectInputStream;

import edu.emory.mathcs.nlp.common.util.IOUtils;
import edu.emory.mathcs.nlp.common.util.Joiner;
import edu.emory.mathcs.nlp.component.dep.DEPParser;
import edu.emory.mathcs.nlp.component.template.NLPComponent;
import edu.emory.mathcs.nlp.component.template.OnlineComponent;
import edu.emory.mathcs.nlp.component.template.config.NLPConfig;
import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.component.template.util.NLPFlag;
import edu.emory.mathcs.nlp.component.template.util.NLPUtils;
import edu.emory.mathcs.nlp.component.template.util.TSVReader;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class DEPDecode
{
	/** This method is included in {@link NLPUtils} from version 1.1.0. */
	static public NLPComponent getComponent(InputStream in)
	{
		ObjectInputStream oin = IOUtils.createObjectXZBufferedInputStream(in);
		OnlineComponent<?> component = null;
		
		try
		{
			component = (OnlineComponent<?>)oin.readObject();
			component.setFlag(NLPFlag.DECODE);
			oin.close();
		}
		catch (Exception e) {e.printStackTrace();}

		return component;
	}
	
	public void main(String[] args) throws Exception
	{
		final String configurationFile = "configuration-file.xml";
		final String modelFile = "model-file.xz";
		final String inputFile = "input-file.tsv";
		
		DEPParser parser = (DEPParser)getComponent(IOUtils.createFileInputStream(modelFile));
		NLPConfig config = new NLPConfig(IOUtils.createFileInputStream(configurationFile));
		TSVReader reader = config.getTSVReader();
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
