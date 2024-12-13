<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
  
    const api_root = $page.url.origin;
    let schuhen = [];
  
    function getSchuhen() {
    var config = {
      method: "get",
      url: api_root + "/api/schuhe",
    };

    axios(config)
      .then(function (response) {
        kleidungen = response.data;
      })
      .catch(function (error) {
        alert("Konnte keine Schuhe abrufen");
        console.log(error);
      });
  }
    
    onMount(getSchuhen);
  
    const bildpfade = {
        blau: {
      FRAUENSCHUH: '',
      MAENNERSCHUH: '',
    },
    gruen: {
      FRAUENSCHUH: '',
      MAENNERSCHUH: '',
    },
    schwarz: {
      FRAUENSCHUH: "",
      MAENNERSCHUH: "",
    },
    weiss: {
      FRAUENSCHUH: "",
      MAENNERSCHUH: "",
    },
    grau: {
      FRAUENSCHUH: "",
      MAENNERSCHUH: "",
    },
    braun: {
      FRAUENSCHUH: "",
      MAENNERSCHUH: "",
    },
    rot: {
      FRAUENSCHUH: "",
      MAENNERSCHUH: "",
    },
  };
  
  function getBildpfad(schuhe) {
    const schuheType = schuhe.schuheType ? schuhe.schuheType.toUpperCase() : '';
    const schuheFarbe = schuhe.schuheFarbe ? schuhe.schuheFarbe.toLowerCase() : '';

    const bildpfad = bildpfade[schuheFarbe] && bildpfade[schuheFarbe][schuheType];
    console.log("schuheType:", schuheType);
    console.log("schuheFarbe:", schuheFarbe);
    console.log("bildpfad:", bildpfad);

    return bildpfad || ''; // absoluten Pfad
  }
  </script>
  <br>
  <h1 class="mt-3">Alle Schuhe</h1>
  
  <p>Hallo lieber Nutzer, hier sehen Sie alle unsere aktuellen Schuhe. Stöbern Sie durch unser vielfältiges Angebot und entdecken Sie Ihren perfekten Schuh.</p>

  <table class="table">
    <thead>
      <tr>
        <th scope="col">Marke</th>
        <th scope="col">Beschreibung</th>
        <th scope="col">Bild</th>
        <th scope="col">Mieten</th>
      </tr>
    </thead>
    <tbody>
      {#each schuhen as schuhe (schuhe)}
        <tr>
          <td>{schuhe.marke}</td>
          <td>{schuhe.detailSchuheBeschreibung}</td>
          <td>
            <img src={getBildpfad(schuhe)} alt="Schuhbild" class="frauenschuh-bild" />
          </td>
          <td>
            <button
                        type="button"
                        class="btn btn-primary btn-sm"
                        on:click={() => {
                        
                            location.reload();
                        }}
                    >
  Mieten
</button>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
  
 
  