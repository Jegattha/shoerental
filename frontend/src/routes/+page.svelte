<script>
    import { goto } from '$app/navigation';
    import { isAuthenticated, user } from "../store";
    import auth from "../auth.service";
  
    function goToLogin() {
      goto('/home'); // Navigiere zur Login-Seite
    }
  </script>
  

  {#if !$isAuthenticated}

    <h1>Willkommen bei Shoerental!</h1>
    <div class="container">
        <div class="row">
          <div class="col-sm">
    <p> Bei Shoerental bieten wir Ihnen die Möglichkeit, stilvolle Damen- und Herrenschuhe zu mieten, anstatt sie zu kaufen. In einer Welt, in der Nachhaltigkeit immer wichtiger wird, setzen wir auf die Idee des Teilens und der Wiederverwendung, um die Umwelt zu schützen und gleichzeitig Ihr Modeerlebnis zu bereichern. </p> 
    <p> Warum teure Schuhe kaufen, die Sie nur für einen Anlass tragen, wenn Sie sie bei uns bequem mieten können? Shoerental ermöglicht es Ihnen, hochwertige Schuhe für jeden Anlass zu tragen – ob Hochzeit, Geschäftsmeeting oder ein Abendessen – ohne dabei Ihr Budget zu sprengen. </p> 
    <p> Mit Shoerental tragen Sie nicht nur dazu bei, Modeabfälle zu reduzieren, sondern entdecken auch eine neue Art, Ihren persönlichen Stil zu entfalten. Unsere Plattform bietet Ihnen Zugang zu einer exklusiven Kollektion von Schuhen, die regelmäßig aktualisiert wird, damit Sie immer die neuesten Trends ausprobieren können. </p>
     <p> Werden Sie Teil der Shoerental-Familie und erleben Sie den Luxus, Ihre Schuhwahl an jeden Anlass anzupassen, ohne Kompromisse bei Stil oder Nachhaltigkeit einzugehen. Entdecken Sie unsere Kollektion und starten Sie noch heute Ihre Reise zu einem umweltbewussten und stilvollen Leben! </p>
   

  
    <button class="btn btn-primary mt-3" on:click={goToLogin}>
      Jetzt anmelden
    </button>
    
  </div>
</div>
</div>
{/if}

<div class="image-container">
  <img src="/images/luxuryHerrenschuhe.png" alt="Herrenschuh" width="400" />
  <img src="/images/luxuryDamenschuhe.png" alt="Damenschuhe" width="400" />
</div>

<style>

h1, p {
  text-align: center;

}


  .image-container {
    display: flex;            /* Verwende Flexbox */
    justify-content: center;  /* Zentriert die Bilder horizontal */
    gap: 20px;                /* Abstand zwischen den Bildern */
   margin-top: 5px;         
  }


</style>