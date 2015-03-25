/*
 * Copyright 2015 Alfresco Software, Ltd.  All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd. 
 * pursuant to a written agreement and any use of this program without such an 
 * agreement is prohibited. 
 */
package org.alfresco.services;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.httpclient.AlfrescoHttpClient;
import org.alfresco.httpclient.AuthenticationException;
import org.alfresco.httpclient.GetRequest;
import org.alfresco.httpclient.Response;
import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;
import org.alfresco.services.solr.GetTextContentResponse;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.URLEncoder;

/**
 * 
 * @author sglover
 *
 */
public class ContentGetterImpl implements ContentGetter
{
    private static final String GET_CONTENT = "api/solr/textContent";

    private static final Log logger = LogFactory.getLog(ContentGetterImpl.class);

//    private String alfrescoHost = "localhost";
//    private String baseUrl = "/alfresco";
//    private int alfrescoPort = 8080;
//    private int alfrescoPortSSL = 8443;
//    private int maxTotalConnections = 10;
//    private int maxHostConnections = 10;
//    private int socketTimeout = 60000;
//
//    private String sslKeyStoreType = "JCEKS";
//    private String sslKeyStoreProvider = null;
//    private String sslKeyStoreLocation = "ssl.repo.client.keystore";
//    private String sslKeyStorePasswordFileLocation = "ssl-keystore-passwords.properties";
//    private String sslTrustStoreType = "JCEKS";
//    private String sslTrustStoreProvider = null;
//    private String sslTrustStorePasswordFileLocation = "ssl-truststore-passwords.properties";
//    private String sslTrustStoreLocation = "ssl.repo.client.truststore";

//	private String username;
//	private String password;
//    private SessionFactory cmisFactory;
//    private Session cmisSession;

    private AlfrescoHttpClient repoClient;

	public ContentGetterImpl(AlfrescoHttpClient repoClient)
	{
		this.repoClient = repoClient;
//		this.cmisFactory = SessionFactoryImpl.newInstance();
	}

//    protected AlfrescoHttpClient getRepoClient(SecureCommsType commsType)
//    {
//        // TODO i18n
//        KeyStoreParameters keyStoreParameters = new KeyStoreParameters("SSL Key Store", sslKeyStoreType, sslKeyStoreProvider, sslKeyStorePasswordFileLocation, sslKeyStoreLocation);
//        KeyStoreParameters trustStoreParameters = new KeyStoreParameters("SSL Trust Store", sslTrustStoreType, sslTrustStoreProvider, sslTrustStorePasswordFileLocation, sslTrustStoreLocation);
//        SSLEncryptionParameters sslEncryptionParameters = new SSLEncryptionParameters(keyStoreParameters, trustStoreParameters);
//
//        KeyResourceLoaderImpl keyResourceLoader = new KeyResourceLoaderImpl();
//
//        HttpClientFactory httpClientFactory = new HttpClientFactory(commsType,
//                sslEncryptionParameters, keyResourceLoader, null, null, alfrescoHost,
//                alfrescoPort, alfrescoPortSSL, maxTotalConnections, maxHostConnections, socketTimeout);
//        // TODO need to make port configurable depending on secure comms, or just make redirects
//        // work
//        AlfrescoHttpClient repoClient = httpClientFactory.getRepoClient(alfrescoHost, alfrescoPortSSL);
//        repoClient.setBaseUrl(baseUrl);
//        return repoClient;
//    }

//    private Session getCMISSession()
//    {
//    	if(cmisSession == null)
//    	{
//    		Map<String, String> parameters = new HashMap<String, String>();
//    		parameters.put(SessionParameter.USER, username);
//    		parameters.put(SessionParameter.PASSWORD, password);
//    		parameters.put(SessionParameter.BROWSER_URL, "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/browser");
//    		parameters.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
//    		parameters.put(SessionParameter.REPOSITORY_ID, "-default-");
//
////    		try
////    		{
//    			cmisSession = cmisFactory.createSession(parameters);
////    		}
////    		catch(ConnectException e)
////    		{
////    			
////    		}
//    	}
//
//    	return cmisSession;
//    }

//	public InputStream getContent(String nodeId)
//	{
//		InputStream is = null;
//
//		ObjectId objectId = new ObjectIdImpl(nodeId);
//
//    	ContentStream stream = getCMISSession().getContentStream(objectId);
//    	if(stream != null)
//    	{
//	    	is = stream.getStream();
//    	}
//
//    	return is;
//	}

	public GetTextContentResponse getTextContent(Long nodeId, QName propertyQName, Long modifiedSince) throws AuthenticationException, IOException
    {
        StringBuilder url = new StringBuilder(128);
        url.append(GET_CONTENT);
        
        StringBuilder args = new StringBuilder(128);
        if(nodeId != null)
        {
            args.append("?");
            args.append("nodeId");
            args.append("=");
            args.append(nodeId);            
        }
        else
        {
            throw new NullPointerException("getTextContent(): nodeId cannot be null.");
        }
        if(propertyQName != null)
        {
            if(args.length() == 0)
            {
                args.append("?");
            }
            else
            {
                args.append("&");
            }
            args.append("propertyQName");
            args.append("=");
            args.append(URLEncoder.encode(propertyQName.toString()));
        }
        
        url.append(args);
        
        GetRequest req = new GetRequest(url.toString());
        
        if(modifiedSince != null)
        {
            Map<String, String> headers = new HashMap<String, String>(1, 1.0f);
            headers.put("If-Modified-Since", String.valueOf(DateUtil.formatDate(new Date(modifiedSince))));
            req.setHeaders(headers);
        }

        Response response = repoClient.sendRequest(req);

        if(response.getStatus() != HttpServletResponse.SC_NOT_MODIFIED &&
                response.getStatus() != HttpServletResponse.SC_NO_CONTENT &&
                response.getStatus() != HttpServletResponse.SC_OK)
        {
            throw new AlfrescoRuntimeException("GetTextContentResponse return status is " + response.getStatus());
        }

        return new GetTextContentResponse(response);
	}

    public GetTextContentResponse getTextContent(long nodeId) throws AuthenticationException, IOException
    {
    	long start = System.currentTimeMillis();

    	GetTextContentResponse response = null;

    	try
    	{
    		response = getTextContent(nodeId, ContentModel.PROP_CONTENT, 0l);
	    	return response;
    	}
    	finally
    	{
        	long end = System.currentTimeMillis();
        	logger.debug("Text content get time = " + (end - start));
    	}
    }
}
