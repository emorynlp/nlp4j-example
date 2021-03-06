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

import edu.emory.mathcs.nlp.common.util.IOUtils;
import edu.emory.mathcs.nlp.component.template.node.NLPNode;
import edu.emory.mathcs.nlp.decode.NLPDecoder;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class NLPDecoderDemo
{
	static public void main(String[] args) throws Exception
	{
		final String configurationFile = "edu/emory/mathcs/nlp/configuration/config-decode-en.xml";
		
		NLPDecoder decoder = new NLPDecoder(IOUtils.getInputStream(configurationFile));
		String sentence = "Jinho Choi is a professor at Emory University.";
		NLPNode[] nodes = decoder.decode(sentence);
		System.out.println(decoder.toString(nodes));
	}
}
