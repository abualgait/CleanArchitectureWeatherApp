package com.weather.app.app_feature.responses

object MockWebServerResponses {
    const val searchResults = "[\n" +
            "  {\n" +
            "    \"Key\": \"1\",\n" +
            "    \"LocalizedName\": \"Cairo\",\n" +
            "    \"Country\": {\n" +
            "      \"Key\": \"EG\",\n" +
            "      \"LocalizedName\": \"Egypt\"\n" +
            "    },\n" +
            "    \"AdministrativeArea\": {\n" +
            "      \"Key\": \"CAI\",\n" +
            "      \"LocalizedName\": \"Cairo\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"Key\": \"2\",\n" +
            "    \"LocalizedName\": \"Alex\",\n" +
            "    \"Country\": {\n" +
            "      \"Key\": \"EG\",\n" +
            "      \"LocalizedName\": \"Egypt\"\n" +
            "    },\n" +
            "    \"AdministrativeArea\": {\n" +
            "      \"Key\": \"ALX\",\n" +
            "      \"LocalizedName\": \"Alex\"\n" +
            "    }\n" +
            "  }\n" +
            "]\n"


    const val hourlyForecastData = "[\n" +
            "  {\n" +
            "    \"DateTime\": \"2023-12-07T12:00:00\",\n" +
            "    \"IconPhrase\": \"Partly Cloudy\",\n" +
            "    \"WeatherIcon\": 3,\n" +
            "    \"Temperature\": {\n" +
            "      \"Value\": 25,\n" +
            "      \"Unit\": \"Celsius\"\n" +
            "    },\n" +
            "    \"Link\": \"https://example.com/hourly/1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"DateTime\": \"2023-12-07T15:00:00\",\n" +
            "    \"IconPhrase\": \"Sunny\",\n" +
            "    \"WeatherIcon\": 1,\n" +
            "    \"Temperature\": {\n" +
            "      \"Value\": 28,\n" +
            "      \"Unit\": \"Celsius\"\n" +
            "    },\n" +
            "    \"Link\": \"https://example.com/hourly/2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"DateTime\": \"2023-12-07T18:00:00\",\n" +
            "    \"IconPhrase\": \"Cloudy\",\n" +
            "    \"WeatherIcon\": 4,\n" +
            "    \"Temperature\": {\n" +
            "      \"Value\": 22,\n" +
            "      \"Unit\": \"Celsius\"\n" +
            "    },\n" +
            "    \"Link\": \"https://example.com/hourly/3\"\n" +
            "  }\n" +
            "]\n"

    const val dailyForecastResponse = "{\n" +
            "  \"DailyForecasts\": [\n" +
            "    {\n" +
            "      \"Date\": \"2023-12-07\",\n" +
            "      \"Temperature\": {\n" +
            "        \"Minimum\": {\n" +
            "          \"Value\": 20,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        },\n" +
            "        \"Maximum\": {\n" +
            "          \"Value\": 30,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"Day\": {\n" +
            "        \"Icon\": 2,\n" +
            "        \"IconPhrase\": \"Cloudy\"\n" +
            "      },\n" +
            "      \"Link\": \"https://example.com/daily/1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Date\": \"2023-12-08\",\n" +
            "      \"Temperature\": {\n" +
            "        \"Minimum\": {\n" +
            "          \"Value\": 18,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        },\n" +
            "        \"Maximum\": {\n" +
            "          \"Value\": 28,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"Day\": {\n" +
            "        \"Icon\": 1,\n" +
            "        \"IconPhrase\": \"Sunny\"\n" +
            "      },\n" +
            "      \"Link\": \"https://example.com/daily/2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"Date\": \"2023-12-09\",\n" +
            "      \"Temperature\": {\n" +
            "        \"Minimum\": {\n" +
            "          \"Value\": 22,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        },\n" +
            "        \"Maximum\": {\n" +
            "          \"Value\": 32,\n" +
            "          \"Unit\": \"Celsius\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"Day\": {\n" +
            "        \"Icon\": 3,\n" +
            "        \"IconPhrase\": \"Partly Cloudy\"\n" +
            "      },\n" +
            "      \"Link\": \"https://example.com/daily/3\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n"

    const val currentConditions = "[\n" +
            "  {\n" +
            "    \"LocalObservationDateTime\": \"2023-12-07T13:45:00+02:00\",\n" +
            "    \"EpochTime\": 1670496300,\n" +
            "    \"WeatherText\": \"Partly Cloudy\",\n" +
            "    \"WeatherIcon\": 3,\n" +
            "    \"HasPrecipitation\": true,\n" +
            "    \"PrecipitationType\": \"Rain\",\n" +
            "    \"IsDayTime\": true,\n" +
            "    \"Temperature\": {\n" +
            "      \"Metric\": {\n" +
            "        \"Value\": 25.5,\n" +
            "        \"Unit\": \"Celsius\",\n" +
            "        \"UnitType\": 17\n" +
            "      },\n" +
            "      \"Imperial\": {\n" +
            "        \"Value\": 77.9,\n" +
            "        \"Unit\": \"Fahrenheit\",\n" +
            "        \"UnitType\": 18\n" +
            "      }\n" +
            "    },\n" +
            "    \"MobileLink\": \"https://example.com/weather/mobile/1\",\n" +
            "    \"Link\": \"https://example.com/weather/1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"LocalObservationDateTime\": \"2023-12-07T16:30:00+02:00\",\n" +
            "    \"EpochTime\": 1670507400,\n" +
            "    \"WeatherText\": \"Clear\",\n" +
            "    \"WeatherIcon\": 1,\n" +
            "    \"HasPrecipitation\": false,\n" +
            "    \"PrecipitationType\": null,\n" +
            "    \"IsDayTime\": true,\n" +
            "    \"Temperature\": {\n" +
            "      \"Metric\": {\n" +
            "        \"Value\": 28.2,\n" +
            "        \"Unit\": \"Celsius\",\n" +
            "        \"UnitType\": 17\n" +
            "      },\n" +
            "      \"Imperial\": {\n" +
            "        \"Value\": 82.8,\n" +
            "        \"Unit\": \"Fahrenheit\",\n" +
            "        \"UnitType\": 18\n" +
            "      }\n" +
            "    },\n" +
            "    \"MobileLink\": \"https://example.com/weather/mobile/2\",\n" +
            "    \"Link\": \"https://example.com/weather/2\"\n" +
            "  }\n" +
            "]\n"
}
