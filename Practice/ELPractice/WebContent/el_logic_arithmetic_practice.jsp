<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- EL is required -->
<%@ page isELIgnored ="false" %>
<!-- This page practice EL Logic and Arithmetic operators
	num_one = 1, num_two = 2 -->
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>EL Math Practice</title>
		<link href="css/testPage.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="content">
			<div class="practice_block">
				<div class="parctice_name">Logic practice: &gt;, &lt;, &gt;=, &lt;=, ==, !=</div>
				<div class="practice_body">
					<!-- 1 > 2? -->
					<div>
						<div class="practice_element_left">
							${num_one} &gt; ${num_two}?
						</div>
						<div class="practice_element_right">
							${num_one > num_two}, ${num_one gt num_two};
						</div>
					</div>
					<!-- 1 < 2? -->
					<div>
						<div class="practice_element_left">
							${num_one} &lt; ${num_two}?
						</div>
						<div class="practice_element_right">
							${num_one < num_two}, ${num_one lt num_two};
						</div>
					</div>
					<!-- 1 >= 1? -->
					<div>
						<div class="practice_element_left">
							${num_one} &gt;= ${1}?
						</div>
						<div class="practice_element_right">
							${num_one >= 1}, ${num_one ge 1};
						</div>
					</div>
					<!-- 1 <= 1? -->
					<div>
						<div class="practice_element_left">
							${num_one} &lt;= ${1}?
						</div>
						<div class="practice_element_right">
							${num_one <= 1}, ${num_one le 1};
						</div>
					</div>
					<!-- 2 == 2? -->
					<div>
						<div class="practice_element_left">
							2 == ${num_two}?
						</div>
						<div class="practice_element_right">
							${2 == num_two}, ${2 eq num_two};
						</div>
					</div>
					<!-- 1 != 2? -->
					<div>
						<div class="practice_element_left">
							${num_one} != ${num_two}?
						</div>
						<div class="practice_element_right">
							${num_one != num_two}, ${num_one ne num_two};
						</div>
					</div>
				</div>
				<div class="parctice_name">Logic practice: &amp;&amp;, ||, !</div>
				<div class="practice_body">
					<!-- true && true? -->
					<div>
						<div class="practice_element_left">
							true &amp;&amp; true?
						</div>
						<div class="practice_element_right">
							${true && true}, ${true and true}
						</div>
					</div>
					<!-- true && false? -->
					<div>
						<div class="practice_element_left">
							true &amp;&amp; false?
						</div>
						<div class="practice_element_right">
							${true && false}, ${true and false}
						</div>
					</div>
					<!-- true || true? -->
					<div>
						<div class="practice_element_left">
							true || true?
						</div>
						<div class="practice_element_right">
							${true || true}, ${true or true}
						</div>
					</div>
					<!-- true || false? -->
					<div>
						<div class="practice_element_left">
							true || false?
						</div>
						<div class="practice_element_right">
							${true || false}, ${true or false}
						</div>
					</div>
					<!-- true && !true? -->
					<div>
						<div class="practice_element_left">
							true &amp;&amp; !true?
						</div>
						<div class="practice_element_right">
							${true && !true}, ${true and not true}
						</div>
					</div>
					<!-- true || !true? -->
					<div>
						<div class="practice_element_left">
							true || !true?
						</div>
						<div class="practice_element_right">
							${true || !true}, ${true or not true}
						</div>
					</div>
				</div>
				<div class="parctice_name">Logic practice: ?, :</div>
				<div class="practice_body">
					<!-- 1 > 2? 'IS_TRUE' : 'IS_FALSE' -->
					<div>
						<div class="practice_element_left">
							1 &gt; 2?
						</div>
						<div class="practice_element_right">
							${1 > 2? 'IS_TRUE' : 'IS_FALSE'}
						</div>
					</div>
				</div>
			</div>
			<div class="practice_block">
				<div class="parctice_name">Math practice: +, -, *, /, %, ()</div>
				<div class="practice_body">
					<!-- 11 + 2 -->
					<div>
						<div class="practice_element_left">
							11 + 2?
						</div>
						<div class="practice_element_right">
							${11 + 2}
						</div>
					</div>
					<!-- 11 - 2 -->
					<div>
						<div class="practice_element_left">
							11 - 2?
						</div>
						<div class="practice_element_right">
							${11 - 2}
						</div>
					</div>
					<!-- 11 * 2 -->
					<div>
						<div class="practice_element_left">
							11 * 2?
						</div>
						<div class="practice_element_right">
							${11 * 2}
						</div>
					</div>
					<!-- 11 / 2 -->
					<div>
						<div class="practice_element_left">
							11 / 2?
						</div>
						<div class="practice_element_right">
							${11 / 2}
						</div>
					</div>
					<!-- 11 mod 2 -->
					<div>
						<div class="practice_element_left">
							11 mod 2?
						</div>
						<div class="practice_element_right">
							${11 % 2}
						</div>
					</div>
					<!-- 1 + 2 * 3 -->
					<div>
						<div class="practice_element_left">
							1 + 2 * 3?
						</div>
						<div class="practice_element_right">
							${1 + 2 * 3}
						</div>
					</div>
					<!-- (1 + 2) * 3 -->
					<div>
						<div class="practice_element_left">
							(1 + 2) * 3?
						</div>
						<div class="practice_element_right">
							${(1 + 2) * 3}
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>