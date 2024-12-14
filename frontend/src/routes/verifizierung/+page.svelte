<script>

    import { isAuthenticated, user } from "../../store";
    import { onMount } from "svelte";
    import { jwt_token } from "../../store";
    
        let name = "";
        let email = "";
        let adresse = "";
        let telefonnummer = "";
        let rolle = "";
      
        const handleSubmit = async () => {
        const mailData = {
          to: "shoerental1@gmail.com", // Ihre Ziel-E-Mail-Adresse
          subject: "Neue Verifizierungsanfrage",
          message: `Name: ${name}\nE-Mail: ${email}\nAdresse: ${adresse}\nTelefonnummer: ${telefonnummer}\nRolle: ${rolle}`,
        };
    
        try {
          const response = await fetch("/api/service/verifizierung/sendMail", {
            method: "POST",
            headers: { 
              "Content-Type": "application/json",
              Authorization: "Bearer "+$jwt_token
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
      };
      
      </script>
      
      <style>
        /* Rahmen und Stil für das Kontaktformular */
        form {
          max-width: 300px;
          margin: 10px auto;
          padding: 10px;
          border: 2px solid #4caf50; /* Grüner Rahmen */
          border-radius: 6px;
          background-color: #f9f9f9;
          box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }
      
        label {
          font-weight: bold;
          margin-top: 20px;
          display: block;
          color: #333;
        }
      
        button {
          width: 100%;
          padding: 20px;
          background-color: #4caf50; /* Grüner Hintergrund */
          color: white;
          border: none;
          border-radius: 2px;
          font-size: 15px;
          font-weight: bold;
          cursor: pointer;
        }
      
        button:hover {
          background-color: #45a049;
        }
      
        img {
          display: block;
          margin: 10px auto;
          max-width: 100%;
          border-radius: 5px;
        }
      
        h1, h3 {
          text-align: center;
          color: #333;
        }
      
        p {
          text-align: center;
          color: #555;
          max-width: 500px;
          margin: 0 auto 10px auto;
        }
      </style>
      
      <h1>Verfizierung vom Account</h1>
      
      <p>
        Damit sie unseren Service benutzen möchten, müssen wir Sie zuerst verfifizieren. 
      </p>
      
      <h3>Verifizierung</h3>
    
    {#if $isAuthenticated}
      <form on:submit|preventDefault={handleSubmit}>
        <label for="name">Name:</label>
        <input id="name" bind:value={name} type="text" placeholder="Ihr Name" required>
      
        <label for="email">E-Mail:</label>
        <input id="email" bind:value={email} type="email" placeholder="Ihre E-Mail-Adresse" required>
      
        <label for="subject">Adresse:</label>
        <input id="subject" bind:value={adresse} type="number" placeholder="Adresse" required>
    
        <label for="subject">Telefonnummer:</label>
        <input id="subject" bind:value={telefonnummer} type="number" placeholder="Telefonnummer" required>
    
        <label for="rolle">Rolle:</label>
            <select id="rolle" bind:value={rolle} required>
                <option value="" disabled selected>Wählen Sie Ihre Rolle</option>
                <option value="mieter">Mieter</option>
                <option value="vermieter">Vermieter</option>
            </select>
      
        <button type="submit">Senden</button>
      </form>
    {/if}
      
      <img src="/images/dog.png" alt="Doggo" width="400" />
      