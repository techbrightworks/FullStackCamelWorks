import groovy.util.slurpersupport.NodeChild
import groovy.util.slurpersupport.NodeChildren
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil


def values = "${body}"
def response = new XmlSlurper().parseText(values)
def sb = new StringBuffer()
if(values.startsWith("<firework>")){
sb.append("The FireWorks Update");
sb.append(System.getProperty("line.separator"))
sb.append(response.id)
sb.append(":")
sb.append(" ")
sb.append(response.name)
sb.append(":")
sb.append(" ")
sb.append(response.description )
sb.append(System.getProperty("line.separator"))
}
else{
sb.append("The FireWorks Search Details");
sb.append(System.getProperty("line.separator"))
response.firework.each{ firework->
	sb.append(firework.id)
	sb.append(":")
	sb.append(" ")
	sb.append(firework.name)
	sb.append(":")
	sb.append(" ")
	sb.append(firework.description )
	sb.append(System.getProperty("line.separator"))
}
	
}
exchange.getIn().setBody(sb.toString());
