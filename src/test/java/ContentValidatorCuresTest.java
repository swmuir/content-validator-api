import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.junit.Ignore;
import org.junit.Test;
import org.sitenv.contentvalidator.dto.ContentValidationResult;
import org.sitenv.contentvalidator.dto.enums.ContentValidationResultLevel;
import org.sitenv.contentvalidator.dto.enums.SeverityLevel;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.service.ContentValidatorService;

public class ContentValidatorCuresTest extends ContentValidatorTester {
	
	private static final String CURES_SCENARIO_DIRECTORY = TEST_RESOURCES_DIRECTORY + "/cures/ref";
	private static HashMap<String, CCDARefModel> refModelHashMap = loadAndParseScenariosAndGetRefModelHashMap(
			CURES_SCENARIO_DIRECTORY);
	private static ContentValidatorService validator = new ContentValidatorService(refModelHashMap);
	static {
		println();
		println("Keys: " + refModelHashMap.keySet());
		println("Values: " + refModelHashMap.values());
	}	
	
	private static final boolean LOG_RESULTS_TO_CONSOLE = true;
	
	private static final String B1_TOC_AMB_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Amb";
	private static final String B1_TOC_INP_VALIDATION_OBJECTIVE = "170.315_b1_ToC_Inp";
	
	private static final String E1_VDT_AMB_VALIDATION_OBJECTIVE = "170.315_e1_VDT_Amb";
	private static final String E1_VDT_INP_VALIDATION_OBJECTIVE = "170.315_e1_VDT_Inp";
	
	private static final String G9_APIACCESS_INP_VALIDATION_OBJECTIVE = "170.315_g9_APIAccess_Inp"; 
	
	private static final String REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA = "170.315_g9_api_access_inp_sample1.xml";
	
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF = "170.315_b1_toc_amb_sample1_v1.pdf";
	
	private static final String REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY = "170.315_b1_toc_amb_sample3_v2.xml";
	private static final String REF_CURES_B1_INP_AMB_SAMPLE3_JANE = "170.315_b1_toc_inp_sample3.xml";
	
	private static final String REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE = "170.315_e1_vdt_amb_sample1.xml";
	private static final String REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA = "170.315_e1_vdt_inp_sample1.xml";

	private static final String MOD_REF_CURES_ADD_AUTHORS = "ModRef_AddAuthors_b1TocAmbCcdR21Aample1V13.xml";
	private static final String MOD_REF_CURES_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1 = "ModRef_CuresNoBirthSex_b1TocAmbSample1.xml";
	private static final String MOD_REF_CURES_MODREF_REMOVE_VITAL_SIGNS_OBS_AUTHORS_E1_VDT_INP_SAMPLE1_REBECCA = 
			"ModRef_RemoveVitalSignsObsAuthors_E1VdtInpSample1.xml";
	private static final String MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1 = 
			"ModRef_AddNotesActivityEncounterEntry_b1TocAmbS1.xml";
	private static final String MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1 = 
			"ModRef_AddNotesActivityProcActProcEntryRel_b1TocAmbS1.xml";	
	private static final String MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME = 
			"ModRef_AccurateDateAndTimeForAuthor_DocLvl_VitalSection_g9AAInpS1.xml";
	
	
	private static final int SUB_CURES_MISSING_AUTHOR_IN_HEADER = 0;
	private static final int SUB_HAS_BIRTH_SEX = SUB_CURES_MISSING_AUTHOR_IN_HEADER;
	private static final int SUB_EF = 1;
	private static final int SUB_NO_BIRTH_SEX = SUB_EF;
	private static final int SUB_MATCH_REF_B1_TOC_AMB_SAMPLE3 = 2;
	private static final int SUB_HAS_TELECOM_MISMATCHES = 3;
	private static final int SUB_HAS_NOTE_ACTIVITY_WITH_AUTHOR_IN_PROCEDURES = 4;
	private static final int SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES = 5;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITH_AUTHOR_IN_RESULTS = 6;
	private static final int SUB_HAS_RESULT_ORGANIZER_WITHOUT_AUTHOR_IN_RESULTS = 7;
	private static final int SUB_LAB_RESULTS_NOT_FOUND_SITE_3199 = 8;
	private static final int SUB_LAB_RESULTS_STILL_NOT_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_SITE_3199 = 9;
	private static final int SUB_LAB_RESULTS_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_AND_OBS_CODES_SITE_3199 = 10;
	private static final int SUB_SOCIAL_HISTORY_WITHOUT_BIRTH_SEX_OBS_TEMPLATE_SITE_3094 = 11;
	private static final int SUB_SOCIAL_HISTORY_WITH_BIRTH_SEX_OBS_TEMPLATE_SITE_3094 = 12;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 13;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_INP_S3_SITE_3220 = 14;
	private static final int ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 15;
	private static final int ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_INP_S3_SITE_3220 = 16;
	private static final int ADD_SMOKING_STATUS_ENTRY_FORMER_AND_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220 = 17;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_10_AUTHORS_TOTAL_REBECCA_SITE_3232 = 18;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_2_AUTHORS_TOTAL_REBECCA_SITE_3232 = 19;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_4_AUTHORS_TOTAL_REBECCA_SITE_3232 = 20;
	private static final int SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232 = 21;
	private static final int SUB_ADD_2_AUTHORS_TO_PROB_SEC_CONC_OBS_B1_TOC_AMB_CCD_R21_SAMPLE1V13_SITE3235 = 22;
	private static final int HAS_2_AUTHORS_INHEADER_B1_TOC_AMB_S1_SITE_3235 = 23;
	private static final int SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF = 24;
	private static final int SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1 = 25;
	private static final int SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1 = 26;
	private static final int SUB_HAS_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 27;
	private static final int SUB_HAS_DATE_ONLY_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 28;
	private static final int SUB_HAS_DATE_ONLY_INVERSE_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 29;
	private static final int SUB_HAS_ACCURATE_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 30;
	private static final int SUB_HAS_MIXED_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241 = 31;

	private static URI[] SUBMITTED_CCDA = new URI[0];
	static {
		try {
			SUBMITTED_CCDA = new URI[] {
					ContentValidatorCuresTest.class.getResource("cures/sub/RemoveAuthorInHeader_170.315_b1_toc_amb_ccd_r21_sample1_v13.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/C-CDA_R2-1_CCD_EF.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/170.315_b1_toc_amb_sample3.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("preCures/sub/170.315_b1_toc_amb_sample1_Submitted_T1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddNoteActivityWithAuthorToProcedures_b1_toc_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddAuthorToProceduresProcedureActivityProcedure_b1_toc_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddAuthorToResultsResultOrganizer_e1_vdt_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddResultOrganizerWithoutAuthorToResults_e1_vdt_amb_s1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/HasNullFlavorOnResultOrganizerCode.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/DoesNotHaveNullFlavorOnResultOrganizerCode.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/DoesNotHaveNullFlavorOnResultOrganizerObservationCodes.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/SocialHistoryWithoutBirthSexObsTemplateSite3094.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/SocialHistoryWithBirthSexObsTemplateSite3094.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddSmokingStatusEntryFormerSmoker_b1_toc_amb_s3_Site3220.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddSmokingStatusEntryFormerSmoker_b1_inp_amb_s3_Site3220.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddSmokingStatusEntryUnknownSmoker_b1_toc_amb_s3_Site3220.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddSmokingStatusEntryUnknownSmoker_b1_inp_amb_s3_Site3220.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/AddSmokingStatusEntryFormerAndUnknownSmoker_b1_toc_amb_s3_Site3220.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/vitalSignsSectionWith5Organizers10Observations_10AuthorsTotal_rebecca_Site3232.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/vitalSignsSectionWith5Organizers10Observations_2AuthorsTotal_rebecca_Site3232.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/vitalSignsSectionWith5Organizers10Observations_4AuthorsTotal_rebecca_Site3232.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/vitalSignsSectionWith5Organizers10Observations_0AuthorsTotal_rebecca_Site3232.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/Add2AuthorsToProbSecConcObs_b1TocAmbCcdR21Sample1V13_Site3235.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/Has2AuthorsInHeader_b1TocAmbS1_Site3235.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/170.315_b1_toc_amb_sample1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/ModRef_AddNotesActivityEncounterEntry_b1TocAmbS1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/ModRef_AddNotesActivityProcActProcEntryRel_b1TocAmbS1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/hasDateAndTimeForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/hasDateOnlyForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/hasDateOnlyInverseForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/ref/ModRef_AccurateDateAndTimeForAuthor_DocLvl_VitalSection_g9AAInpS1.xml").toURI(),
					ContentValidatorCuresTest.class.getResource("cures/sub/hasMixedDateAndTimeForAuthorTimeInDocLevAndVitalSignsSite3241.xml").toURI()					
			};
		} catch (URISyntaxException e) {
			if(LOG_RESULTS_TO_CONSOLE) e.printStackTrace();
		}
	}
	
	private static final URI URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER = SUBMITTED_CCDA[SUB_CURES_MISSING_AUTHOR_IN_HEADER];		
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileAsString);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, String ccdaFileAsString) {
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileAsString, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, String ccdaFileAsString, SeverityLevel severityLevel) {
		return validator.validate(validationObjective, referenceFileName, ccdaFileAsString, true, severityLevel);
	}

	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, referenceFileName, ccdaFileURI);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, URI ccdaFileURI) {
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileURI, SeverityLevel.INFO);
	}
	
	private ArrayList<ContentValidationResult> validateDocumentAndReturnResultsCures(String validationObjective, 
			String referenceFileName, URI ccdaFileURI, SeverityLevel severityLevel) {
		String ccdaFileAsString = convertCCDAFileToString(ccdaFileURI);
		return validateDocumentAndReturnResultsCures(validationObjective, referenceFileName, ccdaFileAsString, severityLevel);
	}
	 
	@Test
	public void cures_basicContentValidationTest() {
		printHeader("cures_basicContentValidationTest");
		try {
			ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY, SUBMITTED_CCDA[SUB_EF],
					SeverityLevel.ERROR);
			printResults(results);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void cures_matchingSubAndRefExpectNoIssuesTest() {
		printHeader("cures_matchingSubAndRefExpectNoIssuesTest");

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[SUB_MATCH_REF_B1_TOC_AMB_SAMPLE3], SeverityLevel.ERROR);
		printResults(results);
		
		if (results.size() > 1) {
			fail("There should not be more than 1 error");
		} else if(results.size() == 1) {
			// birth sex is an exception as it is not dependent on what is in the scenario
			// if we have a message, and it is not the birth sex exception, we fail
			// zero is a pass, so we don't mention
			if (!resultsContainMessage("The scenario requires patient's birth sex to be captured as part of social history data, "
					+ "but submitted file does not have birth sex information", results, ContentValidationResultLevel.ERROR)) {
				fail("The submitted and reference files match so there should not be any results yet there are results");
			}
		}		
	}
	
	@Test
	public void cures_telecomTest() {
		printHeader("telecomTest");
		
		// Cures enforces an ERROR for telecom issues as opposed to a warning with non-cures
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES]);
		assertFalse("No results were returned", results.isEmpty());
		println("FINAL RESULTS");
		println("No of Entries = " + results.size());
		
		final String telecomMessage = "Patient Telecom in the submitted file does not match the expected Telecom";
		assertTrue("The results do not contain the expected message of: " + telecomMessage, 
				resultsContainMessage(telecomMessage, results, ContentValidationResultLevel.ERROR));
		
		printResults(results);
	}	
	
	@Test
	public void cures_birthSexTest() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Sub missing birth sex returns an ERROR for curesUpdate or a WARNING for non-cures (2015) 
		// as per regulation https://www.healthit.gov/isa/uscdi-data/birth-sex
		// Note: Even though the birthSexMessage implies birth sex is required because it is in the scenario, 
		// we require it regardless of it being there or not - 
		// the source code (CCDARefModel.validateBirthSex ) purposely does not even reference the scenario, only the submitted file.
		
		String birthSexMessage = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";
		
		ArrayList<ContentValidationResult> results;
		
		// *** these tests are written in a future proof manner, knowing that birth sex will be added to all the scenarios ***
		
		printHeader("Ref has birth sex. Sub does not have birth sex. Expect birth sex error");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("Expect birth sex error: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));
		
		printHeader("Ref does not have have birth sex. Sub does not have birth sex. "
				+ "Still expect birth sex error despite ref not requiring it");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_NO_BIRTH_SEX_B1_TOC_AMB_SAMPLE1, SUBMITTED_CCDA[SUB_NO_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("Expect birth sex error despite ref not requiring it: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));
		
		printHeader("Ref has birth sex. Sub has birth sex. Expect NO birth sex error as sub has birth sex");
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_BIRTH_SEX], SeverityLevel.ERROR);
		printResults(results);
		assertFalse("Expect NO birth sex error as sub has birth sex but got: " + birthSexMessage, 
				resultsContainMessage(birthSexMessage, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_labResultsNotFoundDueToNullFLavorOnResultOrganizerCodeSite3199Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Result Organizer has nullFlavor on code but not part of parsing so irrelevant
		// Result Organizer/Observation/code HAS nullFlavor on all 7 instances
		// the first 6 of which should not have nullFlavor as they also have data.
		// The nullFlavor is causing nothing to be parsed in the sub, and therefore no lab data to be found
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_NOT_FOUND_SITE_3199], SeverityLevel.ERROR);			
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertTrue("Results should have contained the following message but did not: " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_labResultsStillNotFoundDueToNoNullFLavorOnResultOrganizerObservationCodesSite3199Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Result Organizer does not have nullFlavor on code but not part of parsing so irrelevant 
		// and therefore doesn't fix C-CDA issue
		// Result Organizer/Observation/code still HAS nullFlavor on all 7 instances
		// the first 6 of which should not have nullFlavor as they also have data.
		// The nullFlavor is causing nothing to be parsed in the sub, and therefore no lab data to be found
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_STILL_NOT_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_SITE_3199],
				SeverityLevel.ERROR);		
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertTrue("Results should have contained the following message but did not: " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_labResultsAreFoundDueToNoNullFLavorOnResultOrganizerObservationCodesSite3199Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Result Organizer has nullFlavor on code but not part of parsing so irrelevant
		// Result Organizer/Observation/code does not have nullFlavor on the first 6 instances
		// which allows for a pass due to it being able to parse the codes/lab results.
		// 7th instance is not needed and has no data so nullFlavor actually makes sense
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_LAB_RESULTS_FOUND_REMOVED_NULL_FLAVOR_ORG_CODE_AND_OBS_CODES_SITE_3199],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String missingLabResultsMessage = "The scenario requires data related to patient's lab results, "
				+ "but the submitted C-CDA does not contain lab result data."; 
		assertFalse("Results should not have contained the following message : " + missingLabResultsMessage, 
				resultsContainMessage(missingLabResultsMessage, results, ContentValidationResultLevel.ERROR));
	}	
	
	@Test
	public void cures_severityLevelLimitTestFileWithThreeErrorsOnly() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		final String error1 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = MC and telecom/@value = tel:+1(555)-777-1234";
		final String error2 = "Patient Telecom in the submitted file does not match the expected Telecom. "
				+ "The following values are expected: telecom/@use = HP and telecom/@value = tel:+1(555)-723-1544";
		final String error3 = "The scenario requires patient's birth sex to be captured as part of social history data, "
				+ "but submitted file does not have birth sex information";

		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF, SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.INFO);
		printResults(results);
		assertTrue("expecting 3 errors", results.size() == 3);
		ContentValidationResultLevel expectedSeverity = ContentValidationResultLevel.ERROR;
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);

		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.WARNING);
		printResults(results);
		assertTrue("expecting (the same) 3 errors", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);
		
		results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_TELECOM_MISMATCHES], SeverityLevel.ERROR);
		printResults(results);
		assertTrue("expecting (the same) 3 errors", results.size() == 3);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error1, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error2, expectedSeverity);
		severityLevelLimitTestHelperAssertMessageAndSeverity(results, error3, expectedSeverity);
	}	
		
	@Test
	public void cures_authorinHeaderTest() {		
		printHeader("cures_authorinHeaderTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
				
		final String noAuthorDataMessage = "The scenario requires data related to Author (Provenance), "
				+ "but the submitted C-CDA does not contain Author data.";
		assertTrue("The results do not contain the expected message of: " + noAuthorDataMessage, 
				resultsContainMessage(noAuthorDataMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorinAllergiesTest() {
		printHeader("cures_authorinAllergiesTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Allergy Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInAllergiesSectionRootLevel = "The scenario requires Provenance data for Allergy Section "
				+ "however the submitted file does not contain the Provenance data for Allergy Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergiesSectionRootLevel, 
				resultsContainMessage(noAuthorInAllergiesSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Allergy Section/AllergyConcern author missing in sub but required in modified ref
		final String noAuthorInAllergiesSectionAllergyConcernMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Allergy Section/AllergyConcern, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergiesSectionAllergyConcernMessage, 
				resultsContainMessage(noAuthorInAllergiesSectionAllergyConcernMessage, results, ContentValidationResultLevel.ERROR));
		
		// Allergy Section/AllergyConcern/AllergyObservation author missing in sub but required in modified ref
		final String noAuthorInAllergyObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Allergy Section/AllergyConcern/AllergyObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergyObsMessage, 
				resultsContainMessage(noAuthorInAllergyObsMessage, results, ContentValidationResultLevel.ERROR));

		// Allergy Section/AllergyConcern/AllergyObservation/AllergyReaction author missing in sub but required in modified ref
		// Note: Adding author to AllergyReaction is NOT a rule in the IG. But, it is an open standard. And, it's part of our model. 
		final String noAuthorInAllergyReactionMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Allergy Section/AllergyConcern/AllergyObservation/AllergyReaction, "
				+ "however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInAllergyReactionMessage, 
				resultsContainMessage(noAuthorInAllergyReactionMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorinProblemsTest() {
		printHeader("cures_authorinProblemsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Problem Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInProblemSectionRootLevel = "The scenario requires Provenance data for Problem Section "
				+ "however the submitted file does not contain the Provenance data for Problem Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionRootLevel, 
				resultsContainMessage(noAuthorInProblemSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Problem Section/ProblemConcern author missing in sub but required in modified ref
		final String noAuthorInProblemSectionProblemConcern = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionProblemConcern, 
				resultsContainMessage(noAuthorInProblemSectionProblemConcern, results, ContentValidationResultLevel.ERROR));
		
		// Problem Section/ProblemConcern/ProblemObservation author missing in sub but required in modified ref
		final String noAuthorInProblemSectionProblemConcernProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern/ProblemObservation, "
				+ "however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProblemSectionProblemConcernProblemObservation, 
				resultsContainMessage(noAuthorInProblemSectionProblemConcernProblemObservation, results, ContentValidationResultLevel.ERROR));
		
		/* Past Medical History Section/ProblemObservation author missing in sub but required in modified ref
		   Note: This one is NOT in the Problems Section in C-CDA but is part of our CCDAProblem model as a CCDAProblemObs pastIllnessProblems, which it contains
		   We parse it from the Past Medical History (V3) [section: identifier urn:hl7ii:2.16.840.1.113883.10.20.22.2.20:2015-08-01(open)] which can contain a Problem Observation (V3) (optional)
		   Parsed with:
		   PAST_ILLNESS_EXP = CCDAConstants.CCDAXPATH.compile("
		   /ClinicalDocument/component/structuredBody/component/section[not(@nullFlavor) and code[@code='11348-0']]");
		   PAST_ILLNESS_PROBLEM_OBS_EXPRESSION = CCDAConstants.CCDAXPATH.compile("
		   ./entry/observation[not(@nullFlavor) and templateId[@root='2.16.840.1.113883.10.20.22.4.4']]"); */
		final String noAuthorInPastMedicalHistoryProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Past Medical History Section (Past Illness)/ProblemObservation, however the submitted data had only 0 entries";
		assertTrue("The results do not contain the expected message of: " + noAuthorInPastMedicalHistoryProblemObservation, 
				resultsContainMessage(noAuthorInPastMedicalHistoryProblemObservation, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_authorInProceduresTest() {
		printHeader("cures_authorInProceduresTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Procedures Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInProceduresSectionRootLevel = "The scenario requires Provenance data for Procedures Section "
				+ "however the submitted file does not contain the Provenance data for Procedures Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProceduresSectionRootLevel, 
				resultsContainMessage(noAuthorInProceduresSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Procedures Section/ProcedureActivityProcedure author missing in sub but required in modified ref
		final String noAuthorInProcedureActivityProcedure = "The scenario requires a total of 2 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInProcedureActivityProcedure, 
				resultsContainMessage(noAuthorInProcedureActivityProcedure, results, ContentValidationResultLevel.ERROR));
		
		// TODO-db: Add more tests for remaining potential sub entries				
	}
	
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubProceduresProcedureActivityProcedureTest() {
		// SITE-3193
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_PROCEDURE_ACTIVITY_PROCEDURE_WITH_AUTHOR_IN_PROCEDURES], SeverityLevel.ERROR);
		printResults(results);
		
		// Procedures Section/ProcedureActivityProcedure author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}	
	
	// TODO: Analyze: Seems like a bug that this is being identified as Procedure Activity Procedure when it is Note Activity, check parser/check with dragon. Do we enforce author here too?
	// To see the bug we must comment out 'if (refAuths != null && refAuths.size() != 0) {' compareAuthors in CCDAAuthore and run this test
	// Try to find a way to repro with current code legitimately and go from there...
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubProceduresNoteActivityTest() {
		// SITE-3193 (done), SITE-3194 (not yet done, see todo above)		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[SUB_HAS_NOTE_ACTIVITY_WITH_AUTHOR_IN_PROCEDURES], SeverityLevel.ERROR);
		printResults(results);
		
		// Procedures Section/Note Activity author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Procedures Section/ProcedureActivityProcedure, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_authorInMedicationsTest() {
		printHeader("cures_authorInMedicationsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Medications Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInMedicationsSectionRootLevel = "The scenario requires Provenance data for Medications Section "
				+ "however the submitted file does not contain the Provenance data for Medications Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInMedicationsSectionRootLevel, 
				resultsContainMessage(noAuthorInMedicationsSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Medications Section/MedicationActivity author missing in sub but required in modified ref
		final String noAuthorInMedicationActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Medications Section/MedicationActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInMedicationActivity, 
				resultsContainMessage(noAuthorInMedicationActivity, results, ContentValidationResultLevel.ERROR));
		
		// TODO-db: Look at parser, may be more authors to collect
	}
	
	@Test
	public void cures_authorInImmunizationsTest() {
		printHeader("cures_authorInImmunizationsTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Immunizations Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInImmunizationsSectionRootLevel = "The scenario requires Provenance data for Immunizations Section "
				+ "however the submitted file does not contain the Provenance data for Immunizations Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInImmunizationsSectionRootLevel, 
				resultsContainMessage(noAuthorInImmunizationsSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Immunizations Section/ImmunizationActivity author missing in sub but required in modified ref
		final String noAuthorInImmunizationActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Immunizations Section/ImmunizationActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInImmunizationActivity, 
				resultsContainMessage(noAuthorInImmunizationActivity, results, ContentValidationResultLevel.ERROR));				
	}
	
	@Test
	public void cures_authorInResultsTest() {
		printHeader("cures_authorInResultsTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Results Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInResultsSectionRootLevel = "The scenario requires Provenance data for Results Section "
				+ "however the submitted file does not contain the Provenance data for Results Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultsSectionRootLevel, 
				resultsContainMessage(noAuthorInResultsSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Results Section/ResultsOrganizer author missing in sub but required in modified ref
		final String noAuthorInResultsSectionResultOrganizerMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Results Section/ResultOrganizer, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultsSectionResultOrganizerMessage, 
				resultsContainMessage(noAuthorInResultsSectionResultOrganizerMessage, results, ContentValidationResultLevel.ERROR));
		
		// Results Section/ResultsOrganizer/ResultsObservation author missing in sub but required in modified ref
		final String noAuthorInResultObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Results Section/ResultOrganizer/ResultObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultObsMessage, 
				resultsContainMessage(noAuthorInResultObsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_ZeroAuthorsInRefOneOrMoreInSubResultsResultOrganizerTest() {
		// SITE-3189
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_HAS_RESULT_ORGANIZER_WITH_AUTHOR_IN_RESULTS], SeverityLevel.ERROR);
		printResults(results);
		
		// Results Section/ResultsOrganizer author missing in ref yet is in sub (sub does have author, ref does not/not require it)
		// There is no situation where we should be requiring 0 of something. The submitted file should be allowed to have more information than the scenario.
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Results Section/ResultOrganizer, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}
	
	@Test
	public void cures_ZeroAuthorsInRefAndZeroInSubResultsResultOrganizerTest() {
		// SITE-3189
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_AMB_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_AMB_SAMPLE1_ALICE,
				SUBMITTED_CCDA[SUB_HAS_RESULT_ORGANIZER_WITHOUT_AUTHOR_IN_RESULTS], SeverityLevel.ERROR);
		printResults(results);
		
		// Results Section/ResultsOrganizer author missing in ref yet and missing in sub
		final String zeroAuthorsInRefOneOrMoreInSub = "The scenario requires a total of 0 Author Entries for "
				+ "Results Section/ResultOrganizer, however the submitted data had only 1 entries.";
		assertFalse("The results should not contain the unexpected message of: " + zeroAuthorsInRefOneOrMoreInSub, 
				resultsContainMessage(zeroAuthorsInRefOneOrMoreInSub, results, ContentValidationResultLevel.ERROR));			
	}		
	
	@Test
	public void cures_authorInVitalSignsTest() {
		printHeader("cures_authorInVitalSignsTest");
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Vital Signs Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInVitalSignsSectionRootLevel = "The scenario requires Provenance data for Vital Signs Section "
				+ "however the submitted file does not contain the Provenance data for Vital Signs Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInVitalSignsSectionRootLevel, 
				resultsContainMessage(noAuthorInVitalSignsSectionRootLevel, results, ContentValidationResultLevel.ERROR));		
		
		// Vital Signs Section/VitalSignsOrganizer author missing in sub but required in modified ref
		final String noAuthorInVitalSignsSectionResultOrganizerMessage = "The scenario requires a total of 1 Author Entries "
				+ "for Vital Signs Section/VitalSignsOrganizer, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInVitalSignsSectionResultOrganizerMessage, 
				resultsContainMessage(noAuthorInVitalSignsSectionResultOrganizerMessage, results, ContentValidationResultLevel.ERROR));
		
		// Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation author missing in sub but required in modified ref
		final String noAuthorInResultObsMessage = "The scenario requires a total of 1 Author Entries for "
				+ "Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInResultObsMessage, 
				resultsContainMessage(noAuthorInResultObsMessage, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_authorInEncountersTest() {
		printHeader("cures_authorInEncountersTest");		
		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(B1_TOC_AMB_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_ADD_AUTHORS, URI_SUB_CURES_MISSING_AUTHOR_IN_HEADER, SeverityLevel.ERROR);
		failIfNoResults(results);
		printResults(results);
		
		// Encounters Section, section level author missing in sub but required in modified ref
		// Note: Adding author to a section is NOT a rule in the IG. But, it is an open standard. And, it's part of our model.
		final String noAuthorInEncountersSectionRootLevel = "The scenario requires Provenance data for Encounters Section "
				+ "however the submitted file does not contain the Provenance data for Encounters Section.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncountersSectionRootLevel, 
				resultsContainMessage(noAuthorInEncountersSectionRootLevel, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity author missing in sub but required in modified ref
		final String noAuthorInEncounterActivity = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivity, 
				resultsContainMessage(noAuthorInEncounterActivity, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses author missing in sub but required in modified ref
		final String noAuthorInEncounterActivityEncounterDiagnoses = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/EncounterDiagnoses, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityEncounterDiagnoses, 
				resultsContainMessage(noAuthorInEncounterActivityEncounterDiagnoses, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation author missing in sub but required in modified ref
		final String noAuthorInEncounterActivityEncounterDiagnosesProblemObservation = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityEncounterDiagnosesProblemObservation, 
				resultsContainMessage(noAuthorInEncounterActivityEncounterDiagnosesProblemObservation, results, ContentValidationResultLevel.ERROR));
		
		// Encounters Section/EncounterActivity/EncounterDiagnoses/ProblemObservation author missing in sub but required in modified ref
		// This is the Problem-Observation-like Indication (V2) observation within EncounterActivity/EncounterDiagnoses
		// Parsed by readEncounterActivity in EncounterParser via the relative observation in the XML (see readEncounterActivity)
		final String noAuthorInEncounterActivityIndication = "The scenario requires a total of 1 Author Entries for "
				+ "Encounters Section/EncounterActivity/Indication, however the submitted data had only 0 entries.";
		assertTrue("The results do not contain the expected message of: " + noAuthorInEncounterActivityIndication, 
				resultsContainMessage(noAuthorInEncounterActivityIndication, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_SocialHistoryWithoutBirthSexObsTemplate_Site3094Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Social History does not have a birth Sex observation entry. Instead, it has Social History Observation (missing extension though) 2.16.840.1.113883.10.20.22.4.38:2015-08-01
		// Expect no exception thrown handling this scenario, and expect a relevant birth sex error if no exception is thrown
		// THe XML for the code element is irrelevant, because the template is not the Birth Sex template
		ArrayList<ContentValidationResult> results = null;
		try {
			results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
					SUBMITTED_CCDA[SUB_SOCIAL_HISTORY_WITHOUT_BIRTH_SEX_OBS_TEMPLATE_SITE_3094], SeverityLevel.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		if (results != null) {
			printResults(results);		
			final String requiresBirthSexError = "The scenario requires patient's birth sex to be captured "
					+ "as part of social history data, but submitted file does not have birth sex information";
			assertTrue("The results do not contain the expected message of: " + requiresBirthSexError, 
					resultsContainMessage(requiresBirthSexError, results, ContentValidationResultLevel.ERROR));
		}
	}
	
	@Test
	public void cures_SocialHistoryWithBirthSexObsTemplate_Site3094Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		

		// Social History has a proper Birth Sex Observation entry. <templateId root="2.16.840.1.113883.10.20.22.4.200" extension="2016-06-01"/>
		// Expect no exception thrown handling this scenario, but do not expect a birth sex error for requiring it, but do expect one for the proper code, M, or F.
		// Notice in the below XML there is no @code, so @code is not handled, but, it's within the Birth Sex template (see file), so template inclusion is handled
		// Also notice that although there is a nullFlavor, it is not an exception for the requirement of the code 
		// because you cannot use a nullFlavor for a fixed single value in C-CDA.
		// <code nullFlavor="UNK" displayName="Birth Sex"/>  
		ArrayList<ContentValidationResult> results = null;
		try {
			results = validateDocumentAndReturnResultsCures(
					B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
					SUBMITTED_CCDA[SUB_SOCIAL_HISTORY_WITH_BIRTH_SEX_OBS_TEMPLATE_SITE_3094], SeverityLevel.ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}
		
		if (results != null) {
			printResults(results);
			
			final String requiresBirthSexError = "The scenario requires patient's birth sex to be captured "
					+ "as part of social history data, but submitted file does not have birth sex information";
			assertFalse("The contain the unexpected message of: " + requiresBirthSexError, 
					resultsContainMessage(requiresBirthSexError, results, ContentValidationResultLevel.ERROR));
			
			final String requiresBirthSexCodeError = "The scenario requires patient's birth sex to use the codes M or F "
					+ "but the submitted C-CDA does not contain either of these codes.";
			assertTrue("The results do not contain the expected message of: " + requiresBirthSexCodeError, 
					resultsContainMessage(requiresBirthSexCodeError, results, ContentValidationResultLevel.ERROR));			
		}
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownIfEverSmoked_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Without 'Unknown' code exception (has former smoker code):
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippet: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownIfEverSmoked_b1_inp_s3_jane_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Without 'Unknown' code exception (has former smoker code):
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippet: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_INP_VALIDATION_OBJECTIVE, REF_CURES_B1_INP_AMB_SAMPLE3_JANE,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_SMOKER_B1_TOC_INP_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeIsEqualToUnknownIfEverSmoked_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// With 'Unknown' code exception:
		// Expect Pass AFTER fix and FAIL in prod BEFORE fix
		// Targeted Snippet: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked" 
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}	
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeIsEqualToUnknownIfEverSmoked_b1_inp_s3_jane_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// With 'Unknown' code exception:
		// Expect Pass AFTER fix and FAIL in prod BEFORE fix
		// Targeted Snippet: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked"
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_INP_VALIDATION_OBJECTIVE, REF_CURES_B1_INP_AMB_SAMPLE3_JANE,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_UNKNOWN_SMOKER_B1_TOC_INP_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_socialHistorySmokingStatusValueCodeNotEqualToUnknownInAtLeastOneEntry_b1_amb_s3_happy_Site3220Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());		
		
		// Has 2 Smoking Status Observation entries, one passes with 'Unknown' exception and one fails since it is not the 'Unknown' exception 
		// Expect one failure error for the fail that is not the 'Unknown' exception
		// Expect Fail AFTER fix and FAIL in prod BEFORE fix (regression test)
		// Targeted Snippets: 
		// 1: <value xsi:type="CD" code="8517006" displayName="Former smoker" codeSystem="2.16.840.1.113883.6.96" />
		// 2: <value xsi:type="CD" code="266927001" displayName="Unknown if ever smoked" codeSystem="2.16.840.1.113883.6.96"/> 
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE3_HAPPY,
				SUBMITTED_CCDA[ADD_SMOKING_STATUS_ENTRY_FORMER_AND_UNKNOWN_SMOKER_B1_TOC_AMB_S3_SITE_3220], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario does not require data related to patient's Smoking Status, "
				+ "but the submitted C-CDA does contain Smoking Status data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations10AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub > ref expect pass (sub only needs at least as many or equal to ref to pass, sub never needs more than ref)
		// From ETT GG: "The fact that the validator is throwing an error for "only" having 10 entries instead of the required 4 seems like a lingering bug."
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		// There are 5 VItal Signs Organizers and 10 VItal Signs Observations (within them, not all evenly spread).
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_10_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);		 
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 10 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations2AuthorsTotal_ExpectError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub < ref expect fail (sub has to have at least as many as ref to pass)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_2_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 2 entries.";
		assertTrue("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations4AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub == ref (non 0) expect pass (if sub has as many as ref, then we are meeting the req)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_4_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 4 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations0AuthorsTotal_ExpectError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub < ref (sub is 0) expect fail (sub has to have at least as many as ref to pass)
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, REF_CURES_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires a total of 4 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertTrue("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_VitalSignsSection5Organizers10Observations0AuthorsTotal_ExpectNoError_Site3232Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub == ref (0) expect pass
		// https://groups.google.com/u/0/g/edge-test-tool/c/8AbZgOd-QgM
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				E1_VDT_INP_VALIDATION_OBJECTIVE, MOD_REF_CURES_MODREF_REMOVE_VITAL_SIGNS_OBS_AUTHORS_E1_VDT_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_VITAL_SIGNS_SECTION_HAS_5_ORGANIZERS_10_OBSERVATIONS_0_AUTHORS_TOTAL_REBECCA_SITE_3232],
				SeverityLevel.ERROR);
		printResults(results);
		
		// actual situation
		final String message = "The scenario requires a total of 0 Author Entries for Vital Signs Section"
				+ "/VitalSignsOrganizer/VitalSignsObservation, however the submitted data had only 0 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_ProblemSectionConcernObsRef1AuthSub2Auths_ExpectNoError_Site3235Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// sub > ref expect pass
		// https://groups.google.com/u/1/g/edge-test-tool/c/a2CRrzzDffY/m/Lt2f7IrYBAAJ
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_CURES_ADD_AUTHORS,
				SUBMITTED_CCDA[SUB_ADD_2_AUTHORS_TO_PROB_SEC_CONC_OBS_B1_TOC_AMB_CCD_R21_SAMPLE1V13_SITE3235],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Problem Section/ProblemConcern/ProblemObservation author missing in sub but required in modified ref
		final String message = "The scenario requires a total of 1 Author Entries for "
				+ "Problem Section/ProblemConcern/ProblemObservation, "
				+ "however the submitted data had only 2 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_Ref1AuthInHeaderSub2AuthsInHeader_ExpectNoError_Site3235Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());

		// sub > ref expect pass
		// https://groups.google.com/u/1/g/edge-test-tool/c/a2CRrzzDffY/m/Lt2f7IrYBAAJ		
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, REF_CURES_B1_TOC_AMB_SAMPLE1_ALICE_DEF,
				SUBMITTED_CCDA[HAS_2_AUTHORS_INHEADER_B1_TOC_AMB_S1_SITE_3235], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario requires a total of 1 Author Entries for Document Level., "
				+ "however the submitted data had only 2 entries.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_NotesActivityInResultsParser_ExpectError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Notes Activity Results entry
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF], SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_NotesActivityInResultsParser_ExpectNoError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Notes Activity Results entry
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_ENCOUNTER_ENTRY_B1_TOC_AMB_S1],
				SeverityLevel.ERROR);			
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_NotesActivityInProceudureParser_ExpectError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Notes Activity in Procedures Procedure Activity Procedure entryRelationship
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_B1_TOC_AMB_SAMPLE1_REF], SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_NotesActivityInProceudureParser_ExpectNoError_Site3153Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		
		// Notes Activity in Procedures Procedure Activity Procedure entryRelationship
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				B1_TOC_AMB_VALIDATION_OBJECTIVE, MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1,
				SUBMITTED_CCDA[SUB_DUPLICATE_OF_MOD_REF_ADD_NOTES_ACTIVITY_PAP_ENTRY_RELATIONSHIP_B1_TOC_AMB_S1],
				SeverityLevel.ERROR);
		printResults(results);
		
		final String message = "The scenario requires data related to patient's Notes, "
				+ "but the submitted C-CDA does not contain Notes data."; 
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}	

	// TODO: Still test for blank org in any way?	
	
	@Test
	public void cures_ProvenanceTimeComparison_DocLevDatesDoNotMatch_SecLevDatesMatchTimeDoesNot_ETTGGFileFix_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
				
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_HAS_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241], SeverityLevel.ERROR);
		printResults(results);		

		// Provenance at the document level (does not match in any way (date or time)):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20210317101614-0500"/> // Note: This also is an invalid time (extra digit), but caught by date mismatch first
		// There is NO orgName in either the ref or the sub (TODO: does there need to be orgName at doc level, I think NOT)
		// Expected result: The following Error should be produced:		
		String message = "The scenario requires Provenance data of Time at the Document Level: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20210317, which does not match"; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// 4: If the scenario includes a date only (no time), then the submitted file must match the exact date, 
		// but is allowed to have time as well, which matches any point in time, but also matches the required C-CDA format.
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date. 
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 2nd occurrence:
		//   <time value="20150622"/>
		//  sub 2nd occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 3rd occurrence:
		//   <time value="20150622"/>
		//  sub 3rd occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.
		// 
		//  ref 4th occurrence:
		//   <time value="20150622"/>
		//  sub 4th occurrence:
		//   <time value="201506221100-0500"/>
		//  Comparison: 
		//   Date matches, sub has extra time info beyond the date.
		//  Expected result: 
		//   A sub is allowed to have more data then the ref as long as it meets the refs reqs.
		//   Therefore, no error is expected for this occurrence.		
		
		// Note: No further organizers contain an author in the ref, 
		// therefore, no more potential relevant errors can/should be created
		// The result(s) we do NOT expect is/are:
		// errorPrefix + "/VitalSignsOrganizer/VitalSignsObservation: The scenario Author Provenance (Time: Value ) is 20150622, but the submitted C-CDA time value 201506221100-0500 " +		
		message = "is either not as precise as the scenario or otherwise formatted improperly.";		
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_ExactDateOnlyDate_DocLvlDif_SecLvlSame_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 1: If the scenario includes a date, then the submitted file must match that date exactly	
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_HAS_DATE_ONLY_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241], SeverityLevel.ERROR);
		printResults(results);

		// Provenance at the document level (does not match):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20210317"/>	
		// Expected result: The following Error should be produced:		
		String message = "The scenario requires Provenance data of Time at the Document Level: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20210317, which does not match."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// 1: If the scenario includes a date, then the submitted file must match that date exactly
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="20150622"/>
		//  Comparison: 
		//   Date matches exactly, date only 
		//  Expected result: 
		//   No error as a match
		// 
		//  The next 3 occurrences are the same as the 1st

		// The result(s) we do NOT expect is/are:
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is ";
		// we are only doing a partial check of the message since the dates match
		// because we can't guess at what the incorrect date might be. So, as long as it
		// doesn't return an error in relation to the date in general, it passes. See
		// commented out end example of a possible date ahead:
		// 19990215, which does not match.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_ExactDateOnlyDate_DocLvlSame_SecLvlDif_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 1: If the scenario includes a date, then the submitted file must match that date exactly	
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE, REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA,
				SUBMITTED_CCDA[SUB_HAS_DATE_ONLY_INVERSE_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241], SeverityLevel.ERROR);
		printResults(results);

		// Provenance at the document level (matches):
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20150622"/>	
		// Expected result: The following Error should be produced:
		String message = "The scenario requires Provenance data of Time at the Document Level: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is "; 
		// 20210317, which does not match.";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// 1: If the scenario includes a date, then the submitted file must match that date exactly
		//
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		//  ref 1st occurrence:
		//   <time value="20150622"/>
		//  sub 1st occurrence:
		//   <time value="19990215"/>
		//  Comparison: 
		//   Date does not match 
		//  Expected result: 
		//   Error as does not match
		// 
		//  The next 3 occurrences are the same as the 1st
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 19990215, which does not match.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_DateAndTime_DocLvlSame_SecLvlSame_Site3241Test() {
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 2: If the scenario also includes a time, then the submitted file must match the precision and format of that time, but not the exact values (reg ex)
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME,
				SUBMITTED_CCDA[SUB_HAS_ACCURATE_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241],
				SeverityLevel.ERROR);
		printResults(results);
		
		// Provenance at the document level
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-0500"/>
		//  Comparison: 
		//   Exact match full precision 
		//  Expected result: Pass		
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		//
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-0500"/>
		//  Comparison: 
		//   Exact match full precision 
		//  Expected result: Pass
		
		String message = "Provenance";
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
	}
	
	@Test
	public void cures_ProvenanceTimeComparison_DateAndTime_DocLvlNotPrecise_SecLvlNotPreciseAndMore_Site3241Test() {		
		printHeader(new Object() {}.getClass().getEnclosingMethod().getName());
		// 2: If the scenario also includes a time, then the submitted file must match the precision and format of that time, but not the exact values (reg ex)
		ArrayList<ContentValidationResult> results = validateDocumentAndReturnResultsCures(
				G9_APIACCESS_INP_VALIDATION_OBJECTIVE,
				MOD_REF_CURES_G9_APIACCESS_INP_SAMPLE1_REBECCA_DOC_AUTH_PRECISE_TO_TIME,
				SUBMITTED_CCDA[SUB_HAS_MIXED_DATE_AND_TIME_FOR_AUTHOR_TIME_IN_DOC_LEV_AND_VITAL_SIGNS_SITE_3241],
				SeverityLevel.ERROR);
		removeBirthSexError(results);
		printResults(results);

		// Provenance at the document level (does not match in any way (date or time)):
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100"/>
		//  Comparison: 
		//   Has no time-zone (has time only)
		//  Expected result: fail		
		String message = "The scenario requires Provenance data of Time at the Document Level: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "201506221100 is either not as precise as the scenario or otherwise formatted improperly."; 
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// ---- Section level below ----		
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S1
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-"/>
		//  Comparison: 
		//   Has dash but no timezone 
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "201506221100- is either not as precise as the scenario or otherwise formatted improperly.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S2
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201901111234-0500"/>
		//  Comparison: 
		//   Has different base level date 
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20190111, which does not match.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// add has different timezone
		// fix all these issues with sub times
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S3
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100*0500"/>
		//  Comparison: 
		//   Has incorrect format (* instead of + or -)
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "201506221100*0500 is either not as precise as the scenario or otherwise formatted improperly.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S4
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-06001234"/>
		//  Comparison: 
		//   Has too many characters in time-zone
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "201506221100-06001234 is either not as precise as the scenario or otherwise formatted improperly.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S5
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="20150622110078-0500"/>
		//  Comparison: 
		//   Has too many characters in time
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "20150622110078-0500 is either not as precise as the scenario or otherwise formatted improperly.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S6
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="2015062211000500"/>
		//  Comparison: 
		//   Missing time-zone separator
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) is 201506221100-0500, but the submitted C-CDA time value "
				+ "2015062211000500 is either not as precise as the scenario or otherwise formatted improperly.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S7
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="292906221100+1234"/>
		//  Comparison: 
		//   Year is wrong and has random time-zone but that part is OK, and uses plus vs minus, also OK, so fails date portion only
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 29290622, which does not match.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// 7: If the scenario includes a time, then the sub must include a time (in general) (has it's own specific error)
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S8
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="29290622"/>
		//  Comparison: 
		//   Sub has date only, but needs a time since ref has one
		//  Expected result: Fail
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
				+ "but the submitted C-CDA (Time: Value) 29290622 does not include time or time-zone data.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));

		// 6: If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S9
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// NO TIME ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to day
		//  Expected result: Fail
		message = "Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Author Provenance (value time element) is required, "
				+ "but the submitted C-CDA does not contain the (value time element) "
				+ "in one of its Vital Signs Section/VitalSignsOrganizer/VitalSignsObservations.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
		
		// 6: If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S10
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// NO TIME ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to time-zone
		//  Expected result: Fail
		message = "Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Author Provenance (value time element) is required, "
				+ "but the submitted C-CDA does not contain the (value time element) "
				+ "in one of its Vital Signs Section/VitalSignsOrganizer/VitalSignsObservations.";
		int noTimeElementMessageCount = 0;
		for (ContentValidationResult r : results) {
			if (r.getMessage().contains(message)) {
				noTimeElementMessageCount++;
			}
		}
		assertTrue("Results should have contained at least 2 instances of the following message but did not: " + message,
				noTimeElementMessageCount > 1);		
		
		/// EXPECT pass
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S11
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-0500"/>
		//  Comparison: 
		//   perfect match
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
				+ "but the submitted C-CDA (Time: Value) 201506221100-0500 does not include time or time-zone data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S12
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506229999-0500"/>
		//  Comparison: 
		//   match to ref date but has dif time (9999)
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
				+ "but the submitted C-CDA (Time: Value) 201506229999-0500 does not include time or time-zone data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S13
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100-6666"/>
		//  Comparison: 
		//   match to ref date but has dif time-zone (6666)
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
				+ "but the submitted C-CDA (Time: Value) 201506221100-6666 does not include time or time-zone data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S14
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// <time value="201506221100+0500"/>
		//  Comparison: 
		//   match to ref date but has dif time-zone separator plus vs minus which is OK
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario Author Provenance (Time: Value) 201506221100-0500 is more precise than the date, "
				+ "but the submitted C-CDA (Time: Value) 201506221100+0500 does not include time or time-zone data.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));		
				
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S15
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// <time value="20150622"/>
		//  Comparison: 
		//   scenario doesn't have precision past date, so sub doesn't need to either
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20150622, which does not match.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		

		// 5: If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S16
		// in ref:
		// no author at all
		// in sub:
		// <time value="20150622"/>
		//  Comparison: 
		//   scenario doesn't have an author at all, ref has date level precision on time
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20150622, which does not match.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// 5: If the scenario does not include a date, but the sub does, that is not an error, as more data is acceptable
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S17
		// in ref:
		// no time at all
		// in sub:
		// <time value="20150622"/>
		//  Comparison: 
		//   scenario doesn't have an time at all, ref has date level precision on time
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20150622, which does not match.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));

		

		// 3: If the scenario does not include a date, then the sub file would not need to include a date.
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S18
		// in ref:
		// no author at all
		// in sub:
		// no author at all
		//  Comparison: 
		//   scenario doesn't have an author at all, nor does ref
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20150622, which does not match.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));	
		
		// 3: If the scenario does not include a date, then the sub file would not need to include a date.
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S19
		// in ref:
		// no time at all
		// in sub:
		// no time at all
		//  Comparison: 
		//   scenario doesn't have an time at all, nor does ref
		//  Expected result: Pass
		message = "The scenario requires Provenance data of Time at the Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation: "
				+ "The scenario date portion of Author Provenance (Time: Value) is 20150622, "
				+ "but the date portion of the submitted C-CDA is 20150622, which does not match.";
		// Since prior error is identical, ensuring that the latest error matches specifically
		assertFalse("Results should not have contained the following message but did: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
		
		// More Expect Fail
		
		// 6: If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S20
		// in ref:
		// <time value="20150622"/>
		// in sub:
		// NO AUTHOR ELEMENT
		//  Comparison: 
		//   Sub has no author element but needs one since ref has one and a time. Refs time element is precise to day
		//  Expected result: Fail
		message = "Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation Author Provenance (value time element) is required, "
				+ "but the submitted C-CDA does not contain the (value time element) "
				+ "in one of its Vital Signs Section/VitalSignsOrganizer/VitalSignsObservations.";
		noTimeElementMessageCount = 0;
		for (ContentValidationResult r : results) {
			if (r.getMessage().contains(message)) {
				noTimeElementMessageCount++;
			}
		}
		assertTrue("Results should have contained at least 3 instances of the following message but did not: " + message,
				noTimeElementMessageCount > 2);
		
		// 6: If the scenario contains any value at all, and the sub does not, an error is produced
		// Provenance at Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation:
		// S21
		// in ref:
		// <time value="201506221100-0500"/>
		// in sub:
		// NO AUTHOR ELEMENT
		//  Comparison: 
		//   Sub has no time element but needs one since ref has one. Refs time element is precise to time-zone
		//  Expected result: Fail
		// This one triggers the author entry count req - because it's the entire author missing vs just a time
		// Note this is 18 instead of 21 because some of the observations are missing authors in ref
		message = "The scenario requires a total of 19 Author Entries for Vital Signs Section/VitalSignsOrganizer/VitalSignsObservation, "
				+ "however the submitted data had only 18 entries.";
		assertTrue("Results should have contained the following message but did not: " + message, 
				resultsContainMessage(message, results, ContentValidationResultLevel.ERROR));
	}
}
