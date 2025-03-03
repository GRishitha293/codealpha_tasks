import java.util.HashMap;
import java.util.Map;

/**
 * Simple chatbot class to handle user queries and generate responses.
 */
public class ChatbotService {
    
    private final Map<String, String> responses;
    
    public ChatbotService() {
        responses = new HashMap<>();
        responses.put("hello", "Hello! How can I assist you today?");
        responses.put("book room", "Sure! Please provide the check-in and check-out dates.");
        responses.put("bye", "Goodbye! Have a great day!");
    }
    
    public String getResponse(String userInput) {
        userInput = userInput.toLowerCase();
        return responses.getOrDefault(userInput, "I'm sorry, I didn't understand that.");
    }
    
    public static void main(String[] args) {
        ChatbotService chatbot = new ChatbotService();
        System.out.println(chatbot.getResponse("hello"));
        System.out.println(chatbot.getResponse("book room"));
        System.out.println(chatbot.getResponse("bye"));
    }
}
