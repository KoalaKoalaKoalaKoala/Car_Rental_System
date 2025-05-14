import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.scss']
})
export class ChatbotComponent {

  userInput: string = ''; // Define userInput property
  messages: { sender: string; text: string }[] = []; // Define messages array
  isBotTyping: boolean = false; // Define isBotTyping property

  constructor(private service: CustomerService) {}


  ngOnInit(): void {
    this.botTyping(); // Call botTyping method on initialization
      // this.messages = [];
  this.service.restartSession().subscribe();
  }


  // Method to handle sending messages
  sendMessage(): void {
    this.isBotTyping = true; // Set bot typing status to true

    if (this.userInput.trim()) {
      // Add user message to the messages array
      
      this.messages.push({ sender: 'user', text: this.userInput });

      // Simulate bot response
      this.botTyping();

      // Clear the input field
      this.userInput = '';
    }
  }


  botTyping(): void {
    this.isBotTyping = true; // Set bot typing status to true
    this.service.generate(this.userInput).subscribe({
      next: (response: any) => {
        console.log(response);
        // Extract the 'generation' property if it exists, otherwise fallback to the response itself
        const botResponse = response?.generation || (typeof response === 'string' ? response : JSON.stringify(response));
        this.messages.push({ sender: 'bot', text: botResponse });
        this.isBotTyping = false; // Set bot typing status to false
      },
      error: (err: any) => {
        console.error('Error generating bot response:', err);
        this.messages.push({ sender: 'bot', text: 'Sorry, something went wrong. Please try again later.' });
        this.isBotTyping = false; // Set bot typing status to false
      }
    });
  }
}
