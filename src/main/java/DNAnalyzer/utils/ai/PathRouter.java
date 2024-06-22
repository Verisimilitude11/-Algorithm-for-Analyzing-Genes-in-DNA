package DNAnalyzer.utils.ai;

import DNAnalyzer.core.DNAAnalysis;
import DNAnalyzer.ui.cli.CmdArgs;
import DNAnalyzer.ui.gui.DNAnalyzerGUI;
import DNAnalyzer.ui.gui.DNAnalyzerGUIFXMLController;
import DNAnalyzer.utils.core.DNATools;
import DNAnalyzer.utils.core.Utils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import picocli.CommandLine;

/**
 * PathRouter class for the DNAnalyzer program.
 *
 * <p>This class handles the routing of the program's execution based on the command line arguments
 * passed to it.
 *
 * @see DNAnalyzer
 * @see CmdArgs
 * @see CommandLine
 * @see Utils
 * @see PathRouter
 * @see Parser
 * @see DNAAnalysis
 * @see DNATools
 * @see Properties
 * @see DNAnalyzerGUIFXMLController
 * @see DNAnalyzerGUI
 */
public class PathRouter {
  /**
   * Parses the message content from a given response string in JSON format.
   *
   * @param response the response string in JSON format from the OpenAI API
   * @return the message content as a String
   */
  private static String parseMessageContent(String response) {
    String jsonString = response;
    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      JSONArray choicesArray = jsonObject.getJSONArray("choices");
      JSONObject choiceObject = choicesArray.getJSONObject(0);
      JSONObject messageObject = choiceObject.getJSONObject("message");
      String messageContent = messageObject.getString("content");
      return messageContent;
    } catch (JSONException e) {
      return null;
    }
  }

  /**
   * Sends a POST request to the OpenAI API with the specified output as the prompt for the GPT-3
   * model.
   *
   * @param output a String representing DNAnalyzer's analysis output
   * @return a String representing the message content generated by the GPT-3 model
   */
  public static String askGPT(String output, String apiKey) {
    output = output.replaceAll("\\r?\\n", "");
    output = output.replaceAll("\\u001b\\[H\\u001b\\[2J", "");
    String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    String AUTHORIZATION_HEADER = "Bearer " + apiKey;
    try {
      URL url = new URL(API_ENDPOINT);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Authorization", AUTHORIZATION_HEADER);
      conn.setDoOutput(true);

      String requestBody =
          """
          {
              "model": "gpt-4o-2024-05-13",
              "messages": [
                  {
                      "role": "user",
                      "content": "In 5 paragraphs, provide comprehensive DNA analysis with actionable insights using technical terms, beyond surface-level information, **DON'T SUMMARIZE THE OUTPUT PROVIDED, let's think step by step**, for experienced biotechnology researchers, avoiding statements that DNA cannot be analyzed, as a chatbot of DNAnalyzer. Also, even if you think you can't analyze it, analyze it anyway - I won't judge if it's wrong so don't worry. DONT SUMMARIZE THE OUTPUT PROVIDED AND DONT JUST INCLUDE THE NUMBERS. YOU HAVE TO EXPLAIN THE SIGNIFICANCE OF EVERYTHING, provide the numbers as evidence, and also give actionable tasks to the user that they can do to get better/improve what they are lacking. Then, answer those questions. Please format it to be outputted to the terminal (so no markdown). Do not include or repeat the text that is provided to you WITHOUT PROVIDING ADDITIONAL CONTEXT. Dont include the version number as the title. Do not use markdown or * symbols in the response as this will be outputted to the Terminal/command line."""
              + output
              + "\"\n"
              + "        }\n"
              + "    ],\n"
              + "    \"temperature\": 0.9\n"
              + "}";
      conn.getOutputStream().write(requestBody.getBytes());

      String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            response = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                response = reader.lines().collect(Collectors.joining());
            }
        }

      return parseMessageContent(response);
    } catch (IOException e) {
      if (e.getMessage().contains("401")) {
        return "Error: Invalid API key. Please check your API key and try again.";
      } else {
        return "Error: " + e.getMessage();
      }
    }
  }

  /**
   * Runs the regular analysis without the AI analysis.
   *
   * @param args the command line arguments
   * @throws InterruptedException
   * @throws IOException
   */
  public static void regular(String[] args) throws InterruptedException, IOException {
    Utils.clearTerminal();
    System.out.println("""
                       Welcome to DNAnalyzer! Please allow up to 10 seconds for the analysis to complete (note: the time may vary based on your hardware).
                       """);
    new CommandLine(new CmdArgs()).execute(args);
    System.out.println(
        "\n**Please set your OPENAI_API_KEY environment variable for an AI analysis of the DNA**");
    System.exit(1);
  }

  /**
   * Runs the AI analysis.
   *
   * @param args the command line arguments
   * @param apiKey the API key for the OpenAI API
   * @throws InterruptedException
   * @throws IOException
   */
  public static void runGptAnalysis(String[] args, String apiKey)
      throws InterruptedException, IOException {
    Utils.clearTerminal();
    System.out.println("""
                       Welcome to DNAnalyzer! Please allow up to 15 seconds for the analysis to complete (note: the time may vary based on your hardware).
                       """);
    // Create a ByteArrayOutputStream to hold the console output
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);

    // Save the old System.out
    PrintStream old = System.out;

    // Set the new System.out to the PrintStream
    System.setOut(ps);

    new CommandLine(new CmdArgs()).execute(args);

    // Restore the old System.out
    System.out.flush();
    System.setOut(old);

    // Get the captured console output as a string
    String output = baos.toString();

    System.out.println(output + "\n-----------------------\n\nAI Analysis (Powered by GPT 4o):\n");
    String res = PathRouter.askGPT(output, apiKey);

    System.out.println(res);
    System.exit(0);
  }
}
