import service.InputParser
val demoInput =
"""
  |root Test 30 41 Orange
  |rectangle 3 5 43 3 Green
  |circle 3 5 3 Red
  |square 3 5 3 Blue
  |square 5 7 4 Orange
  |""".stripMargin

val parseTest = InputParser.inputParse(demoInput)

