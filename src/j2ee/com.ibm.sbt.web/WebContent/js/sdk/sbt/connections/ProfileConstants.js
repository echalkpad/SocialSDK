/*
 * � Copyright IBM Corp. 2012
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
/**
 * Social Business Toolkit SDK.
 * Definition of constants for ProfileService.
 */
define([],function() {
	return sbt.connections.profileConstants = {
		sbtErrorCodes:{
			badRequest         :400
		},
		sbtErrorMessages:{
			null_id        :"Null userId/email/id",
			null_profileId :"Null id",
			args_object	   :"Invalid argument",
			args_profile   :"Invalid profile",
			null_profile   :"Null profile"
		},
		 xpath_profile : {
			 	"entry":			"/a:feed/a:entry",
				"uid":				"a:contributor/snx:userid",
				"name":				"a:contributor/a:name",
				"email":			"a:contributor/a:email",
				"photo":			"a:content/h:div/h:span/h:div/h:img[@class='photo']/@src",			
				"title":			"a:content/h:div/h:span/h:div[@class='title']",
				"organizationUnit":	"a:content/h:div/h:span/h:div[@class='org']/h:span[@class='organization-unit']",
				"fnUrl":			"a:content/h:div/h:span/h:div/h:a[@class='fn url']/@href",			
				"telephoneNumber":	"a:content/h:div/h:span/h:div[@class='tel']",			
				"bldgId":			"a:content/h:div/h:span/h:div/h:span[@class='x-building']",			
				"floor":			"a:content/h:div/h:span/h:div/h:span[@class='x-floor']",
				"streetAddress":	"a:content/h:div/h:span/h:div/h:div[@class='street-address']",
				"extendedAddress":	"a:content/h:div/h:span/h:div/h:div[@class='extended-address x-streetAddress2']",
				"locality":			"a:content/h:div/h:span/h:div/h:span[@class='locality']",
				"postalCode":		"a:content/h:div/h:span/h:div/h:span[@class='postal-code']",
				"region":			"a:content/h:div/h:span/h:div/h:span[@class='region']",
				"countryName":		"a:content/h:div/h:span/h:div/h:div[@class='country-name']",			
				"soundUrl":			"a:content/h:div/h:span/h:div/h:a[@class='sound url']/@href",	
				"summary":			"a:summary",
				"groupwareMail":	"a:content/h:div/h:span/h:div[@class='x-groupwareMail']",				
				"networkProfileId":	"snx:connection/a:contributor[@snx:rel='http://www.ibm.com/xmlns/prod/sn/connection/target']/snx:userid",
				"networkProfileName":	"snx:connection/a:contributor[@snx:rel='http://www.ibm.com/xmlns/prod/sn/connection/target']/a:name",
				"networkProfileEmail":	"snx:connection/a:contributor[@snx:rel='http://www.ibm.com/xmlns/prod/sn/connection/target']/a:email",
                "totalResults"      :"/a:feed/opensearch:totalResults",
                "startIndex"        :"/a:feed/opensearch:startIndex",
                "itemsPerPage"      :"/a:feed/opensearch:itemsPerPage",
				
		},
		_updateMapAttributes: { 
				"bldgId" : "X_BUILDING",
				"floor" : "X_FLOOR",
				"officeName":"X_OFFICE_NUMBER",
				"title":"TITLE",
				"telephoneNumber":"TEL;WORK",
				"workLocation":"ADR;WORK"
			},
		_createMapAttributes : {
				"guid":	"com.ibm.snx_profiles.base.guid",
				"email":"com.ibm.snx_profiles.base.email",
				"uid": "com.ibm.snx_profiles.base.uid",
				"distinguishedName": "com.ibm.snx_profiles.base.distinguishedName",
				"displayName": "com.ibm.snx_profiles.base.displayName",
				"givenNames": "com.ibm.snx_profiles.base.givenNames",
				"surname": "com.ibm.snx_profiles.base.surname",
				"userState":"com.ibm.snx_profiles.base.userState"
			},
		_userIdentifiers :{
				"userId":"userId",
				"email":"email",
				"id":"id"
		},
		_methodName : {
			"getProfile" 	  : "getProfile",
			"updateProfile"   : "updateProfile",
			"createProfile"   : "createProfile",
			"deleteProfile"	  : "deleteProfile",
			"updateProfilePhoto"    : "updateProfilePhoto"
			
		}
	};
});