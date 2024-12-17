<script>

import { isAuthenticated, user } from "../../store";
  import { onMount } from "svelte";
  import { jwt_token } from "../../store";
  
      let name = "";
      let email = "";
      let subject = "";
      let message = "";
    
      const handleSubmit = async () => {
      const mailData = {
        to: "shoerental1@gmail.com", // Ihre Ziel-E-Mail-Adresse
        subject: "Neue Kontaktanfrage",
        message: `Name: ${name}\nE-Mail: ${email}\nBetreff: ${subject}\nMessage: ${message}`,
      };
  
      try {
      const response = await fetch("/api/service/about/sendMail", {
        method: "POST",
        headers: { 
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
          
        },
        body: JSON.stringify(mailData),
      });
  
        if (response.ok) {
          alert("Vielen Dank! Ihre Anfrage wurde gesendet.");
        } else {
          alert("Fehler beim Senden der Anfrage.");
        }
      } catch (error) {
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        console.error(error);
      }
    }
    </script>

<footer class="footer">
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <h3>Kontakt</h3>
        <p>Email: shoerental1@gmail.com</p>
        <p>Telefon: +41 12 345 67 89</p>
      </div>
      </div>
      </div>
      </footer>
    
    <style>
      /* Rahmen und Stil für das Kontaktformular */
      .footer {
  background-color: #4dc1ff;
  color: #ffffff;
  padding: 20px 0;
  position: fixed;
  bottom: 0;
  left: 0;
  width: auto;
  text-align: center; /* Zentriert den Text */
  z-index: 100; /* Fußzeile wird über anderen Elementen angezeigt */
}

      form {
        max-width: 300px;
        margin: 10px auto;
        padding: 10px;
        border: 5px solid #4caf50; /* Grüner Rahmen */
        border-radius: 8px;
        background-color: #f9f9f9;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
      }
    
      label {
        font-weight: bold;
        margin-top: 20px;
        display: block;
        color: #333;
      }
    
      input, textarea {
        width: 100%;
        padding: 20px;
        margin-top: 5px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        font-size: 14px;
        box-sizing: border-box;
      }
    
      button {
        width: 100%;
        padding: 15px;
        background-color: #4caf50; /* Grüner Hintergrund */
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
      }
    
      button:hover {
        background-color: #45a049;
      }
    
      img {
        display: block;
        margin: 20px auto;
        max-width: 100%;
        border-radius: 8px;
      }
    
      h1, h3 {
        text-align: center;
        color: #333;
      }
    
      p {
        text-align: center;
        color: #555;
        max-width: 600px;
        margin: 0 auto 20px auto;
      }
    </style>
    
    <h1>About Shoerental</h1>   
          <p>
            Bei Shoerental bieten wir Ihnen die besten Mietpreise für Damen- und Herrenschuhe. In einer Welt, in der der Kauf von Schuhe oft überflüssig ist, setzen wir auf Nachhaltigkeit und die Idee des Teilens. Warum teure Schuhe kaufen, die Sie nur selten tragen, wenn Sie sie bei uns mieten können?
          </p>
          <p>
            Unsere Plattform hilft nicht nur dabei, die Umwelt zu schützen, indem sie die Produktion von Einweg-Modeartikeln reduziert, sondern spart Ihnen auch Geld. Sie können ein schickes Outfit für besondere Anlässe tragen, ohne Unmengen für Schuhe auszugeben, die oft nur einmal im Leben getragen werden.
          </p>
          <p>
            Mit Shoerental machen Sie einen Schritt in Richtung umweltbewusster Mode und gleichzeitig können Sie sich stilvolle Schuhe anziehen, ohne Ihr Budget zu belasten. Entdecken Sie unser vielfältiges Angebot an Damen- und Herrenschuhe und mieten Sie noch heute Ihre Traumschuhe!
          </p>
          <p>
            Werden Sie Teil der Shoerental-Familie und entdecken Sie unsere exklusive Kollektion von Damen-, Herrenschuhe und vielem mehr. Um tiefer in die Welt der Mode einzutauchen, klicken Sie einfach auf eines der beiden Bilder und lassen Sie sich von unserem einzigartigen Angebot inspirieren.</p>
      

    
    

  <h3>Kontakt</h3>
    <form on:submit|preventDefault={handleSubmit}>
      <label for="name">Name:</label>
      <input id="name" bind:value={name} type="text" placeholder="Ihr Name" required>
    
      <label for="email">E-Mail:</label>
      <input id="email" bind:value={email} type="email" placeholder="Ihre E-Mail-Adresse" required>
    
      <label for="subject">Betreff:</label>
      <input id="subject" bind:value={subject} type="text" placeholder="Betreff" required>
    
      <label for="message">Nachricht:</label>
      <textarea id="message" bind:value={message} rows="5" placeholder="Ihre Nachricht" required></textarea>
    
      <button type="submit">Senden</button>
    </form>

    <img src="/images/dog.png" alt="Doggo" width="400" />
    