<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { onMount } from "svelte";
  import { goto } from '$app/navigation';


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
    DAMENSCHUH: "https://cms.brnstc.de/product_images/287x393_retina/cpro/media/images/product/24/12/100199757217000_0_1733398011603.jpg",
    HERRENSCHUH: "https://s.alicdn.com/@sc04/kf/H7ed92cb9b63a4326b1a221c50f915287X.jpg_720x720q50.jpg",
  },
  gruen: {
    DAMENSCHUH: "https://ae01.alicdn.com/kf/S6977b1209761481cab0dac9e80f48ab6s.jpg",
    HERRENSCHUH: "https://girotti.de/media/catalog/product/cache/4/small_image/423x/17f82f742ffe127f42dca9de82fb58b1/4/4/44349-7-0.jpg",
  },
  schwarz: {
    DAMENSCHUH: "https://i.pinimg.com/474x/4c/8a/f6/4c8af64f0dec19b706d4aa26996e074f.jpg",
    HERRENSCHUH: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfxbUUDuaCmw1bbnF6u9-widaxdQILBJtEwVCKRpaM7S3IP5vSRDgpesBgemdXjB9J3v0&usqp=CAU",
  },
  weiss: {
    DAMENSCHUH: "https://m.media-amazon.com/images/I/6130g32MLtL._AC_SY500_.jpg",
    HERRENSCHUH: "https://www.cityschuh.com/media/d0/ab/be/1621426225/Sneaker-Herren-Mai.jpg",
  },
  grau: {
    DAMENSCHUH: "https://img.joomcdn.net/ed7037a1e3bb3164ffa32e3ebb03ef4f2f203395_original.jpeg",
    HERRENSCHUH: "https://de.florisvanbommel.com/dw/image/v2/ABAM_PRD/on/demandware.static/-/Sites-mastercatalog/default/dw4269463b/images/zoom/SFM-50148_34-01_1G.jpg?sw=700&sh=900&sm=fit",
  },
  braun: {
    DAMENSCHUH: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZyMqGI4LLEUBnUmnUbKHJ_VKdUfXQB6yA_fB18-PsrC7d60NP5X_msGA9WLwxPY0M6Ck&usqp=CAU",
    HERRENSCHUH: "https://i.etsystatic.com/25275415/r/il/dc184d/4568955891/il_570xN.4568955891_csya.jpg",
  },
  rot: {
    DAMENSCHUH: "https://img.joomcdn.net/509185a890662fa3c13f08831968f85b9b88b7b9_original.jpeg",
    HERRENSCHUH: "https://girotti.de/media/catalog/product/cache/4/small_image/423x/17f82f742ffe127f42dca9de82fb58b1/4/2/4236-6-0.jpg",
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

function goToLogin() {
      goto('/home'); // Navigiere zur Login-Seite
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
        <td>{schuhe.schuheBeschreibung}</td>
        <td>
          <img src={getBildpfad(schuhe)} alt="Schuhbild" class="damenschuh-bild" />
        </td>
        <td>
          <button
                      type="button"
                      class="btn btn-primary btn-sm"
                      on:click={goToLogin}>
                       
                    
Mieten
</button>
        </td>
      </tr>
    {/each}
  </tbody>
</table>


