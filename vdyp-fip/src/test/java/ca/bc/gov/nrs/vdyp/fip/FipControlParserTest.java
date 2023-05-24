package ca.bc.gov.nrs.vdyp.fip;

import static ca.bc.gov.nrs.vdyp.test.VdypMatchers.mmHasEntry;
import static ca.bc.gov.nrs.vdyp.test.VdypMatchers.present;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ca.bc.gov.nrs.vdyp.io.parse.BecDefinitionParser;
import ca.bc.gov.nrs.vdyp.io.parse.ControlFileParserTest;
import ca.bc.gov.nrs.vdyp.io.parse.HLNonprimaryCoefficientParserTest;
import ca.bc.gov.nrs.vdyp.io.parse.ResourceParseException;
import ca.bc.gov.nrs.vdyp.io.parse.SP0DefinitionParser;
import ca.bc.gov.nrs.vdyp.io.parse.SiteCurveAgeMaximumParserTest;
import ca.bc.gov.nrs.vdyp.model.BecDefinition;
import ca.bc.gov.nrs.vdyp.model.Region;
import ca.bc.gov.nrs.vdyp.model.SP0Definition;
import ca.bc.gov.nrs.vdyp.model.SiteCurve;
import ca.bc.gov.nrs.vdyp.model.SiteCurveAgeMaximum;
import ca.bc.gov.nrs.vdyp.model.StockingClassFactor;

@SuppressWarnings("unused")
public class FipControlParserTest {

	@Test
	public void testParse() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseBec() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result,
				(Matcher) hasEntry(
						is(BecDefinitionParser.CONTROL_KEY),
						allOf(instanceOf(Map.class), hasEntry(is("AT"), instanceOf(BecDefinition.class)))
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseSP0() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result,
				(Matcher) hasEntry(
						is(SP0DefinitionParser.CONTROL_KEY),
						allOf(instanceOf(List.class), hasItem(instanceOf(SP0Definition.class)))
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseVGRP() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.VOLUME_EQN_GROUPS), allOf(
								// Map of SP0 Aliases
								isA(Map.class), hasEntry(
										isA(String.class), allOf(
												// Map of BEC aliases
												isA(Map.class), hasEntry(
														isA(String.class), isA(Integer.class) // Equation Identifier
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseDGRP() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.DECAY_GROUPS), allOf(
								// Map of SP0 Aliases
								isA(Map.class), hasEntry(
										isA(String.class), allOf(
												// Map of BEC aliases
												isA(Map.class), hasEntry(
														isA(String.class), isA(Integer.class) // Equation Identifier
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseBGRP() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.BREAKAGE_GROUPS), allOf(
								// Map of SP0 Aliases
								isA(Map.class), hasEntry(
										isA(String.class), allOf(
												// Map of BEC aliases
												isA(Map.class), hasEntry(
														isA(String.class), isA(Integer.class) // Equation Identifier
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseGRBA1() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.DEFAULT_EQ_NUM), allOf(
								// Map of SP0 Aliases
								isA(Map.class), hasEntry(
										isA(String.class), allOf(
												// Map of BEC aliases
												isA(Map.class), hasEntry(
														isA(String.class), isA(Integer.class) // Equation Identifier
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseGMBA1() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.EQN_MODIFIERS), allOf(
								// Default Equation
								isA(Map.class), hasEntry(
										isA(Integer.class), allOf(
												// ITG
												isA(Map.class), hasEntry(
														isA(Integer.class), isA(Integer.class) // Reassigned Equation
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseSTK33() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.STOCKING_CLASS_FACTORS), allOf(
								// STK
								isA(Map.class), hasEntry(
										isA(Character.class), allOf(
												// Region
												isA(Map.class),
												hasEntry(
														isA(Region.class), isA(StockingClassFactor.class) // Factors
												)
										)
								)
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE025() throws Exception {
		var parser = new FipControlParser();
		var result = parseWithAppendix(parser, "025 coe/SIEQN.PRM");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.SITE_CURVE_NUMBERS), allOf(
								// Species
								isA(Map.class), hasEntry(isA(String.class), isA(SiteCurve.class))
						)
				)
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE025Empty() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(result, (Matcher) hasEntry(is(FipControlParser.SITE_CURVE_NUMBERS), Matchers.anEmptyMap()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE026() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.SITE_CURVE_AGE_MAX), allOf(
								// Species
								isA(Map.class), hasEntry(isA(Integer.class), isA(SiteCurveAgeMaximum.class))
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	// @Disabled
	public void testParseE026Empty() throws Exception {
		var parser = new FipControlParser();
		var result = parseWithAppendix(parser, "026  ");
		// Map is empty but gives appropriate default values
		assertThat(result, (Matcher) hasEntry(is(FipControlParser.SITE_CURVE_AGE_MAX), Matchers.anEmptyMap()));
		assertThat(
				((Map<Integer, SiteCurveAgeMaximum>) result.get(FipControlParser.SITE_CURVE_AGE_MAX)).get(1),
				(Matcher) allOf(SiteCurveAgeMaximumParserTest.hasAge(Region.COASTAL, is(140.f)))
		);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE040() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.COE_BA), allOf(
								mmHasEntry(present(is(2.0028f)), 0, "AT", 1)
						)
				)
		);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE041() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.COE_DQ), allOf(
								mmHasEntry(present(is(6.6190f)), 0, "AT", 1)
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE043() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.UPPER_BA_BY_CI_S0_P), allOf(
								mmHasEntry(present(is(109.27f)), Region.COASTAL, "AC", 1)
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE050() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.HL_PRIMARY_SP_EQN_P1), allOf(
								mmHasEntry(present(is(1.00160f)), 1, "AC", Region.COASTAL)
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE051() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.HL_PRIMARY_SP_EQN_P2), allOf(
								mmHasEntry(present(is(0.49722f)), 1, "AC", Region.COASTAL)
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE052() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.HL_PRIMARY_SP_EQN_P3), allOf(
								mmHasEntry(present(is(1.04422f)), 1, "AC", Region.COASTAL)
						)
				)
		);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testParseE053() throws Exception {
		var parser = new FipControlParser();
		var result = parser.parse(ControlFileParserTest.class, "FIPSTART.CTR");
		assertThat(
				result, (Matcher) hasEntry(
						is(FipControlParser.HL_NONPRIMARY), allOf(
								mmHasEntry(present(HLNonprimaryCoefficientParserTest.coe(0.86323f, 1.00505f, 1)), "AC", "AT", Region.COASTAL)
						)
				)
		);
	}


	static InputStream addToEnd(InputStream is, String... lines) {
		var appendix = new ByteArrayInputStream(String.join("\r\n", lines).getBytes(StandardCharsets.US_ASCII));
		var result = new SequenceInputStream(is, appendix);
		return result;
	}

	static Map<String, ?> parseWithAppendix(FipControlParser parser, String... lines)
			throws IOException, ResourceParseException {
		var resolver = FipControlParser.fileResolver(ControlFileParserTest.class);
		try (
				InputStream baseIs = ControlFileParserTest.class.getResourceAsStream("FIPSTART.CTR");
				InputStream is = addToEnd(baseIs, lines);
		) {
			return parser.parse(is, resolver);
		}
	}
}
