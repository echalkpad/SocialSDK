/*
 * © Copyright IBM Corp. 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.sbt.automation.core.test.connections;

import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.ibm.commons.util.StringUtil;
import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.sbt.automation.core.test.BaseApiTest;
import com.ibm.sbt.automation.core.utils.Trace;
import com.ibm.sbt.security.authentication.AuthenticationException;
import com.ibm.sbt.services.client.ClientServicesException;
import com.ibm.sbt.services.client.base.datahandlers.EntityList;
import com.ibm.sbt.services.client.connections.forums.Forum;
import com.ibm.sbt.services.client.connections.forums.ForumReply;
import com.ibm.sbt.services.client.connections.forums.ForumService;
import com.ibm.sbt.services.client.connections.forums.ForumTopic;
import com.ibm.sbt.services.client.connections.forums.model.BaseForumEntity;

/**
 * @author mwallace
 *
 */
public class BaseForumsTest extends BaseApiTest {
	
	protected boolean createForum = true;
    protected ForumService forumService;
    protected Forum forum;

    public BaseForumsTest() {
        setAuthType(AuthType.AUTO_DETECT);
    }
    
    @Before
    public void createForum() {
        createContext();
        if (createForum) {
        	String type = "public";
        	if (environment.isSmartCloud()) {
        		type = "private";
        	}
        	String name = createForumName();
        	//System.out.println(name);
            forum = createForum(name, type, name, "tag1,tag2,tag3");
        }
    }
    
    @After
    public void deleteForumAndQuit() {
    	deleteForum(forum);
    	forum = null;
    	destroyContext();
    }
    
    protected ForumTopic createForumTopic(Forum forum, String title, String content) {
    	ForumService forumService = getForumService();
    	
    	ForumTopic forumTopic = new ForumTopic(forumService);
    	forumTopic.setForumUuid(forum.getForumUuid());
    	forumTopic.setTitle(title);
    	forumTopic.setContent(content);
    	
    	try {
    		return forumService.createForumTopic(forumTopic);
    	} catch (Exception fse) {
    		fail("Error creating forum topic", fse);
    	}
    	
    	return null;
    }
    
    protected ForumTopic getForumTopic(String topicUuid, boolean failOnError) {
    	ForumService forumService = getForumService();
    	
    	try {
    		return forumService.getForumTopic(topicUuid);
    	} catch (Exception fse) {
    		if (failOnError) {
    			fail("Error retrieving forum topic", fse);
    		}
    	}
    	
    	return null;
    }
    
    protected ForumReply getForumReply(String replyUuid, boolean failOnError) {
    	ForumService forumService = getForumService();
    	
    	try {
    		return forumService.getForumReply(replyUuid);
    	} catch (Exception fse) {
    		if (failOnError) {
    			fail("Error retrieving forum reply", fse);
    		}
    	}
    	
    	return null;
    }
    
    protected ForumReply createForumReply(ForumTopic forumTopic, String title, String content) {
    	ForumService forumService = getForumService();
    	
    	ForumReply forumReply = new ForumReply(forumService);
    	forumReply.setTopicUuid(forumTopic.getTopicUuid());
    	forumReply.setTitle(title);
    	forumReply.setContent(content);
    	
    	try {
    		return forumService.createForumReply(forumReply);
    	} catch (Exception fse) {
    		fail("Error creating forum reply", fse);
    	}
    	
    	return null;
    }
    
    protected ForumReply getForumReply(String replyUuid) {
    	ForumService forumService = getForumService();
    	
    	try {
    		return forumService.getForumReply(replyUuid);
    	} catch (Exception fse) {
    		fail("Error retrieving forum reply", fse);
    	}
    	
    	return null;
    }
    
    protected String createForumName() {
    	return this.getClass().getName() + "#" + this.hashCode() + " Forum - " + System.currentTimeMillis();
    }
    
    protected String createForumTopicName() {
    	return this.getClass().getName() + "#" + this.hashCode() + " Forum Topic - " + System.currentTimeMillis();
    }
    
    protected ForumService getForumService() {
        if (forumService == null) {
            forumService = new ForumService(getEndpointName());
        }
        return forumService;
    }
    
    protected void assertForumValid(Forum forum, JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertEquals(forum.getForumUuid(), json.getString("getForumUuid"));
        Assert.assertEquals(forum.getTitle(), json.getString("getTitle"));
        //Assert.assertEquals(forum.getSummary(), json.getString("getSummary"));
        Assert.assertEquals(forum.getContent(), json.getString("getContent"));
        Assert.assertEquals(forum.getForumUrl(), json.getString("getForumUrl"));
        Assert.assertEquals(forum.getAuthor().getName(), json.getJsonObject("getAuthor").getString("name"));
        Assert.assertEquals(forum.getAuthor().getEmail(), json.getJsonObject("getAuthor").getString("email"));
        Assert.assertEquals(forum.getAuthor().getId(), json.getJsonObject("getAuthor").getString("userid"));
        //Assert.assertEquals(forum.getContributor().getName(), json.getJsonObject("getContributor").getString("name"));
        //Assert.assertEquals(forum.getContributor().getEmail(), json.getJsonObject("getContributor").getString("email"));
        //Assert.assertEquals(forum.getContributor().getUserid(), json.getJsonObject("getContributor").getString("userid"));
    }
    
    protected void assertForumTopicValid(ForumTopic forumTopic, JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertEquals(forumTopic.getForumUuid(), json.getString("getForumUuid"));
        Assert.assertEquals(forumTopic.getTopicUuid(), json.getString("getTopicUuid"));
        Assert.assertEquals(forumTopic.getThreadRecommendationCount(), json.getLong("getThreadRecommendationCount"));
        Assert.assertEquals(forumTopic.getTopicUuid(), json.getString("getTopicUuid"));
        Assert.assertEquals(forumTopic.getTitle(), json.getString("getTitle"));
        Assert.assertEquals(StringUtil.trim(forumTopic.getContent()), json.getString("getContent"));
        Assert.assertEquals(forumTopic.getAuthor().getName(), json.getJsonObject("getAuthor").getString("name"));
        Assert.assertEquals(forumTopic.getAuthor().getEmail(), json.getJsonObject("getAuthor").getString("email"));
        Assert.assertEquals(forumTopic.getAuthor().getId(), json.getJsonObject("getAuthor").getString("userid"));
    }
    
    protected void assertForumReplyValid(ForumReply forumReply, JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertEquals(forumReply.getTopicUuid(), json.getString("getTopicUuid"));
        Assert.assertEquals(forumReply.getReplyUuid(), json.getString("getReplyUuid"));
        Assert.assertEquals(forumReply.getTopicUuid(), json.getString("getTopicUuid"));
        Assert.assertEquals(forumReply.getTitle(), json.getString("getTitle"));
        Assert.assertEquals(StringUtil.trim(forumReply.getContent()), json.getString("getContent"));
        Assert.assertEquals(forumReply.getAuthor().getName(), json.getJsonObject("getAuthor").getString("name"));
        Assert.assertEquals(forumReply.getAuthor().getEmail(), json.getJsonObject("getAuthor").getString("email"));
        Assert.assertEquals(forumReply.getAuthor().getId(), json.getJsonObject("getAuthor").getString("userid"));
    }
    
    protected void assertForumGetters(JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertNotNull(json.getString("getForumUuid"));
        Assert.assertNotNull(json.getString("getTitle"));
        //Assert.assertNotNull(json.getString("getSummary"));
        Assert.assertNotNull(json.getString("getContent"));
        Assert.assertNotNull(json.getString("getForumUrl"));
        Assert.assertNotNull(json.getJsonObject("getAuthor").getString("name"));
        Assert.assertNotNull(json.getJsonObject("getAuthor").getString("email"));
        Assert.assertNotNull(json.getJsonObject("getAuthor").getString("userid"));
        //Assert.assertNotNull(json.getJsonObject("getContributor").getString("name"));
        //Assert.assertNotNull(json.getJsonObject("getContributor").getString("email"));
        //Assert.assertNotNull(json.getJsonObject("getContributor").getString("userid"));
    }
    
    protected void assertForumProperties(JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertNotNull(json.getString("uid"));
        Assert.assertNotNull(json.getString("title"));
        Assert.assertNotNull(json.getString("updated"));
        Assert.assertNotNull(json.getString("published"));
        Assert.assertNotNull(json.getString("authorName"));
        Assert.assertNotNull(json.getString("authorEmail"));
        Assert.assertNotNull(json.getString("authorUserid"));
        Assert.assertNotNull(json.getString("authorUserState"));
        Assert.assertNotNull(json.getString("content"));
        Assert.assertNotNull(json.getString("categoryTerm"));
        Assert.assertNotNull(json.getString("editUrl"));
        Assert.assertNotNull(json.getString("selfUrl"));
        Assert.assertNotNull(json.getString("alternateUrl"));
        Assert.assertNotNull(json.getString("forumUuid"));
        Assert.assertNotNull(json.getString("moderation"));
        Assert.assertNotNull(json.getString("threadCount"));
        Assert.assertNotNull(json.getString("forumUrl"));
    }
    
    protected void assertForumTopicProperties(JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertNotNull(json.getString("topicUuid"));
        Assert.assertNotNull(json.getString("forumUuid"));
        //Assert.assertNotNull(json.getString("tags"));
        Assert.assertNotNull(json.getString("permissions"));
        Assert.assertNotNull(json.getString("threadCount"));
        Assert.assertNotNull(json.getString("notRecommendedByCurrentUser"));
        Assert.assertNotNull(json.getString("threadRecommendationCount"));
        Assert.assertNotNull(json.getString("recommendationsUrl"));
        Assert.assertNotNull(json.getString("entry"));
        Assert.assertNotNull(json.getString("uid"));
        Assert.assertNotNull(json.getString("id"));
        Assert.assertNotNull(json.getString("title"));
        Assert.assertNotNull(json.getString("updated"));
        Assert.assertNotNull(json.getString("published"));
        Assert.assertNotNull(json.getString("authorName"));
        Assert.assertNotNull(json.getString("authorEmail"));
        Assert.assertNotNull(json.getString("authorUserid"));
        Assert.assertNotNull(json.getString("authorUserState"));
        Assert.assertNotNull(json.getString("content"));
        Assert.assertNotNull(json.getString("categoryTerm"));
        Assert.assertNotNull(json.getString("editUrl"));
        Assert.assertNotNull(json.getString("selfUrl"));
        Assert.assertNotNull(json.getString("alternateUrl"));        
    }
    
    protected void assertForumReplyProperties(JsonJavaObject json) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertNotNull(json.getString("topicUuid"));
        Assert.assertNotNull(json.getString("replyUuid"));
        //Assert.assertNotNull(json.getString("tags"));
        Assert.assertNotNull(json.getString("permissions"));
        Assert.assertNotNull(json.getString("replyTo"));
        Assert.assertNotNull(json.getString("notRecommendedByCurrentUser"));
        Assert.assertNotNull(json.getString("recommendationsUrl"));
        Assert.assertNotNull(json.getString("entry"));
        Assert.assertNotNull(json.getString("uid"));
        Assert.assertNotNull(json.getString("id"));
        Assert.assertNotNull(json.getString("title"));
        Assert.assertNotNull(json.getString("updated"));
        Assert.assertNotNull(json.getString("published"));
        Assert.assertNotNull(json.getString("authorName"));
        Assert.assertNotNull(json.getString("authorEmail"));
        Assert.assertNotNull(json.getString("authorUserid"));
        Assert.assertNotNull(json.getString("authorUserState"));
        Assert.assertNotNull(json.getString("content"));
        Assert.assertNotNull(json.getString("categoryTerm"));
        Assert.assertNotNull(json.getString("editUrl"));
        Assert.assertNotNull(json.getString("selfUrl"));
        Assert.assertNotNull(json.getString("alternateUrl"));        
    }
    
    protected void assertMemberValid(JsonJavaObject json, String forumUuid, String name, String userid, String email, String role) {
        Assert.assertNull("Unexpected error detected on page", json.getString("code"));
        Assert.assertEquals(forumUuid, json.getString("getForumUuid"));
        Assert.assertEquals(name, json.getString("getName"));
        Assert.assertEquals(userid, json.getString("getUserid"));
        if (!environment.isSmartCloud()) {
        	Assert.assertTrue("Expect match "+email+" <> "+json.getString("getEmail"), email.equalsIgnoreCase(json.getString("getEmail")));
        }
        Assert.assertEquals(role, json.getString("getRole"));
    }
    
    protected Forum getLastCreatedForum() {
        Forum forum = null;
        try {
            loginConnections();
            
            ForumService forumService = getForumService();
            EntityList<Forum> forums = forumService.getMyForums();
            forum = (Forum)forums.iterator().next();
            Trace.log("Last created forum: "+forum.getForumUuid());
            Trace.log("Last created forum: "+forum.getPublished());
            Iterator<Forum> i = forums.iterator();
            while (i.hasNext()) {
            	BaseForumEntity c= i.next();
            	Trace.log("Last created forum: "+((Forum)c).getForumUuid());
            	Trace.log("Last created forum: "+c.getTitle());
            	Trace.log("Last created forum: "+c.getPublished());
            }
        } catch (AuthenticationException pe) {
        	if (pe.getCause() != null) {
        		pe.getCause().printStackTrace();
        	}
            Assert.fail("Error authenicating: " + pe.getMessage());
        } catch (Exception cse) {
            fail("Error getting last created forum", cse);
        } 
        
        return forum;
    }
    
    protected Forum getForum(String forumUuid) {
        return getForum(forumUuid, true);
    }
    
    protected Forum getForum(String forumUuid, boolean failOnCse) {
        Forum forum = null;
        try {
            loginConnections();
            
            ForumService forumService = getForumService();
            forum = forumService.getForum(forumUuid);
            Trace.log("Got forum: "+forum.getForumUuid());
        } catch (AuthenticationException pe) {
        	if (pe.getCause() != null) {
        		pe.getCause().printStackTrace();
        	}
            Assert.fail("Error authenicating: " + pe.getMessage());
        } catch (Exception cse) {
        	if (failOnCse) {
        		fail("Error retrieving forum", cse);
        	}
        } 
        return forum;
    }
    
    protected Forum createForum(String title, String type, String content, String tags) {
    	return createForum(title, type, content, tags, true);
    }
    
    protected Forum createForum(String title, String type, String content, String tags, boolean retry) {
        Forum forum = null;
        try {
            loginConnections();
            ForumService forumService = getForumService();
            
        	long start = System.currentTimeMillis();
            forum = new Forum(forumService, "");
            forum.setTitle(title);
            forum.setContent(content);
            forum.setTags(tags);
            forum = forumService.createForum(forum);
            forum = forumService.getForum(forum.getForumUuid());
            
            long duration = System.currentTimeMillis() - start;
            Trace.log("Created test forum: "+forum.getForumUuid() + " took "+duration+"(ms)");
        } catch (AuthenticationException pe) {
        	if (pe.getCause() != null) {
        		pe.getCause().printStackTrace();
        	}
            Assert.fail("Error authenticating: " + pe.getMessage());
        } catch (Exception cse) {
        	// TODO remove this when we upgrade the QSI
        	Throwable t = cse.getCause();
        	if (t instanceof ClientServicesException) {
        		ClientServicesException csex = (ClientServicesException)t;
        		int statusCode = csex.getResponseStatusCode();
        		if (statusCode == 500 && retry) {
        			return createForum(title + " (retry)", type, content, tags, false);
        		}
        	}
            fail("Error creating test forum with title: '"+title+"'", cse);
        } 
        
        return forum;
    }

    protected void deleteForum(Forum forum) {
        if (forum != null) {
            try {
            	loginConnections();
                ForumService forumService = getForumService();
                // TODO should be deleteForum
                forumService.deleteForum(forum.getForumUuid());
            } catch (AuthenticationException pe) {
            	if (pe.getCause() != null) {
            		pe.getCause().printStackTrace();
            	}
                Assert.fail("Error authenicating: " + pe.getMessage());
            } catch (Exception cse) {
                forum = null;
            	// check if forum delete failed because
            	// forum was already deleted
            	Throwable t = cse.getCause();
            	if (t instanceof ClientServicesException) {
            		ClientServicesException csex = (ClientServicesException)t;
            		int statusCode = csex.getResponseStatusCode();
            		if (statusCode == 404) {
            			Trace.log(this.getClass().getName() + " attempt to delete already deleted Forum: " + csex.getLocalizedMessage());
            			return;
            		}
            	}
                fail("Error deleting forum "+forum, cse);
            }
        }
    }
    
    protected void deleteForum(String forumId) {
        if (forumId != null) {
            try {
            	loginConnections();
                ForumService forumService = getForumService();
                forumService.deleteForum(forumId);
            } catch (AuthenticationException pe) {
            	if (pe.getCause() != null) {
            		pe.getCause().printStackTrace();
            	}
                Assert.fail("Error authenicating: " + pe.getMessage());
            } catch (Exception cse) {
                fail("Error deleting forum "+forumId, cse);
            }
        }
    }
    
	protected ForumTopic createForumTopic(Forum forum, ForumTopic topic) throws Exception {
		ForumService forumService = getForumService();
		return forumService.createForumTopic(topic, forum.getUid());
	}
        
    protected void fail(String message, Exception fse) {
    	String failure = message;
    	
    	Throwable cause = fse.getCause();
    	if (cause != null) {
    		cause.printStackTrace();
    		failure += ", " + cause.getMessage();
    	} else {
    		fse.printStackTrace();
    		failure += ", " + fse.getMessage();
    	}
    	
    	Assert.fail(failure);
    }


}
