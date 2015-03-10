package com.atlassian.bamboo.builder;

import com.atlassian.bamboo.configuration.ConfigurationException;
import com.atlassian.bamboo.configuration.DefaultContentHandler;
import com.atlassian.bamboo.configuration.DefaultElementParser;
import com.atlassian.bamboo.configuration.ElementContentElementParser;
import com.atlassian.bamboo.results.tests.TestResultError;
import com.atlassian.bamboo.results.tests.TestResults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class extracts the interesting test information from the ant test results.
 */
public class AntXmlTestResultsParser extends DefaultContentHandler
{
    private static final Log log = LogFactory.getLog(AntXmlTestResultsParser.class);
    private String suiteClassName;
    private TestResults currentTestResult;

    private Set failedTests;
    private Set passedTests;
    private String systemOut;
    private String errorOut;

    public AntXmlTestResultsParser()
    {
        registerElementParser("testsuites", new DefaultElementParser());
        registerElementParser("testsuite", new TestSuiteElementParser());
        registerElementParser("properties", new PropertiesElementParser());
        registerElementParser("property", new PropertyElementParser());
        registerElementParser("testcase", new TestCaseElementParser());
        registerElementParser("error", new ErrorElementParser());
        registerElementParser("failure", new FailureElementParser());
        registerElementParser("system-out", new SystemOutParser());
        registerElementParser("system-err", new SystemErrParser());
        registerElementParser("skipped", new DefaultElementParser());
    }


    /**
     * Extract unit test results from the given maven test report.
     *
     * @param errorReportStream Maven test report (XML) stream
     * @param myAntTestResultsFile file. Ussed being read. used for debuggging.
     */
    public void parse(InputStream errorReportStream, File myAntTestResultsFile)
    {
        failedTests = new HashSet();
        passedTests = new HashSet();

        try
        {
            XMLReader reader = XMLReaderFactory.createXMLReader(DEFAULT_PARSER);

            reader.setContentHandler(this);
            reader.parse(new InputSource(errorReportStream));
        }
        catch (Exception e)
        {
            if (myAntTestResultsFile != null)
            {
                log.error("Failed to parse xml test results. File name: " + myAntTestResultsFile.getName() + ". At path: " + myAntTestResultsFile.getAbsolutePath(), e);
            }
            else
            {
                log.error("Failed to parse xml test results. File was null.", e);
            }
        }
    }


    /**
     * Does the last parsed test report have any errors?
     *
     * @return true if we have detected errors, false otherwise
     */
    public boolean hasErrors()
    {
        return getNumberOfErrors() > 0;
    }


    /**
     * How many errors did we find in ihe last parsed test report
     *
     * @return number of errors found
     */
    public int getNumberOfErrors()
    {
        return failedTests.size();
    }


    /**
     * How many tests did the last parsed test report contain?
     *
     * @return The number of tests
     */
    public int getNumberOfTests()
    {
        return failedTests.size() + passedTests.size();
    }


    /**
     * How many successful tests did the last parsed test report contain?
     *
     * @return The number of successful tests
     */
    public Set getSuccessfulTests()
    {
        if (passedTests != null)
        {
            return passedTests;
        }
        else
        {
            return Collections.EMPTY_SET;
        }
    }


    /**
     * How many failed tests did the last parsed test report contain?
     *
     * @return The number of failed tests
     */
    public Set getFailedTests()
    {
        if (failedTests != null)
        {
            return failedTests;
        }
        else
        {
            return Collections.EMPTY_SET;
        }
    }

    public String getSystemOut()
    {
        return systemOut;
    }

    public String getSystemErr()
    {
        return errorOut;
    }

    public void parse(InputStream myTestInputStream)
    {
        parse(myTestInputStream, null);
    }

    class TestSuiteElementParser extends DefaultElementParser
    {
        public void startElement(Attributes attributes)
        {
            suiteClassName = attributes.getValue("name");
        }
    }

    class PropertiesElementParser extends DefaultElementParser
    {
        public void startElement(Attributes attributes)
        {
        }
    }

    class PropertyElementParser extends DefaultElementParser
    {
        public void startElement(Attributes attributes)
        {
        }
    }

    class TestCaseElementParser extends DefaultElementParser
    {
        public void startElement(Attributes attributes)
        {
            String testName = attributes.getValue("name");
            String testDuration = attributes.getValue("time");

            // ant 1.6 introduced concept of test class names as a suite can be from multiple classes
            // so we should use it if it's available or fall back to using the suite name if its not available
            String className = attributes.getValue("classname");
            if (className == null)
            {
                className = suiteClassName;
            }
            else if (className.indexOf('$') > -1)
            {
                className = suiteClassName;
            }

            currentTestResult = new TestResults(className, testName, testDuration);
        }

        public void endElement()
        {
            if (currentTestResult.hasErrors())
            {
                failedTests.add(currentTestResult);
            }
            else
            {
                passedTests.add(currentTestResult);
            }
            currentTestResult = null;
        }
    }


    class ErrorElementParser extends ElementContentElementParser
    {
        public void endElement()
        {
            TestResultError error = new TestResultError(getElementContent());

            // Error element us usually inside a test case, but sometimes is not
            if (currentTestResult != null)
            {
                currentTestResult.addError(error);
            }
            else
            {
                // Sometimes it completely dies! See http://jira.atlassian.com/browse/BAM-235
                TestResults errorTestResult = new TestResults(suiteClassName, "unknownTestCase", "0.0");
                errorTestResult.addError(error);
                failedTests.add(errorTestResult);
            }
        }
    }


    class FailureElementParser extends ElementContentElementParser
    {
        private String myMessage;

        public void startElement(Attributes attributes)
        {
            super.startElement(attributes);
            myMessage = attributes.getValue("message");
        }

        public void endElement()
        {
            currentTestResult.addError(new TestResultError(myMessage + System.getProperty("line.separator") + getElementContent()));
        }
    }

    class SystemOutParser extends ElementContentElementParser
    {
        public void endElement() throws ConfigurationException
        {
            super.endElement();
            if (currentTestResult != null)
            {
                currentTestResult.setSystemOut(getElementContent());
            }
            else
            {
                systemOut = getElementContent();
            }
        }
    }

    class SystemErrParser extends ElementContentElementParser
    {
        public void endElement() throws ConfigurationException
        {
            super.endElement();
            errorOut = getElementContent();
        }
    }
}