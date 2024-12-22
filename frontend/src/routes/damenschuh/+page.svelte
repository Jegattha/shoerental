<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { onMount } from "svelte";
  import { jwt_token, user} from "../../store";

  const api_root = $page.url.origin;

  let schuhen = [];
  let filteredSchuhen = [];
  let selectedAvailability = "all";
  let selectedSize = "all";
  let selectedPriceSort = "asc";
  let selectedBrand = "all"; 

  function getSchuhen() {
    var config = {
      method: "get",
      url: api_root + "/api/schuhe",
      headers: {Authorization: "Bearer "+$jwt_token},
    };

    axios(config)
      .then(function (response) {
        schuhen = response.data.map(schuhe => ({
          ...schuhe,
          mieten: false,
          mietdauerVon: schuhe.schuheState === "VERMIETET" ? new Date(schuhe.mietdauerVon) : "",
          mietdauerBis: schuhe.schuheState === "VERMIETET" ? new Date(schuhe.mietdauerBis) : "",
        }));
        filteredSchuhen = schuhen.slice();
      })
      .catch(function (error) {
        alert("Could not get schuhen");
        console.log(error);
      });
  }

  function mietSchuhe(schuheId) {
    const selectedSchuhe = filteredSchuhen.find(schuhe => schuhe.schuheId === schuheId);

    if (!selectedSchuhe) {
      console.error("Selected shoes not found");
      return;
    }

    const { mietdauerVon, mietdauerBis } = selectedSchuhe;

    if (!mietdauerVon || !mietdauerBis) {
      console.error("Rental dates are missing");
      alert("Das Datum darf nicht in der Vergangenheit liegen")
      return;
    }

    const mieterId = "6767eee93abb0a44ec56daec";
  const data = {
    schuheId,
    mieterId,
    mietdauerVon,
    mietdauerBis,
  };


    var config = {
      method: "post",
      url: api_root + "/api/service/mietSchuhe",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer "+$jwt_token},
      data,
    };

    axios(config)
      .then(function (response) {
        getSchuhen();
      })
      .catch(function (error) {
        alert("Zuweisung nicht möglich");
        console.log(error);
      });
  }

  function filterSchuhen() {
    filteredSchuhen = schuhen.slice();

    if (selectedAvailability === "available") {
      filteredSchuhen = filteredSchuhen.filter(schuhe => schuhe.schuheState === "VERFUEGBAR");
    } else if (selectedAvailability === "rented") {
      filteredSchuhen = filteredSchuhen.filter(schuhe => schuhe.schuheState === "VERMIETET");
    }

    if (selectedSize !== "all") {
      filteredSchuhen = filteredSchuhen.filter(schuhe => schuhe.groesse === selectedSize);
    }

    if (selectedBrand !== "all") {
      filteredSchuhen = filteredSchuhen.filter(schuhe => schuhe.marke === selectedBrand);
    }

    filteredSchuhen = filteredSchuhen.filter(schuhe => {
      if (schuhe.schuheState === "VERFUEGBAR" || schuhe.schuheState === "VERMIETET") {
      if (schuhe.mietdauerVon && schuhe.mietdauerBis) {
        const vonDatum = new Date(schuhe.mietdauerVon);
        const bisDatum = new Date(schuhe.mietdauerBis);
        const schuhsDatum = new Date(); 

        return schuhsDatum >= vonDatum && schuhsDatum <= bisDatum;
      }
    }
      return true;
    });

    if (selectedPriceSort === "asc") {
      filteredSchuhen.sort((a, b) => a.preis - b.preis);
    } else if (selectedPriceSort === "desc") {
      filteredSchuhen.sort((a, b) => b.preis - a.preis);
    }
  }
  // Hilfsfunktion zur Formatierung des Datums auf 'YYYY-MM-DD'
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}


function handleDateInput(schuhe, schuheType) {
  if (schuhe && schuheType) {
    if (schuhe.mietdauerVon !="" && schuhe.mietdauerBis != "")  {
      const vonDatum = new Date(schuhe.mietdauerVon);
      const bisDatum = new Date (schuhe.mietdauerBis);
      const aktuellesDatum = new Date();

      console.log("vonDatum:", vonDatum);
      console.log("bisDatum:", bisDatum);
      console.log("aktuellesDatum:", aktuellesDatum);

      if (vonDatum < aktuellesDatum || bisDatum < aktuellesDatum || vonDatum > bisDatum) {
       alert("Das ausgewählte Datum liegt in der Vergangenheit oder ist ungültig. Bitte wählen Sie gültige Daten.");
        schuhe.mietdauerVon = "";
        schuhe.mietdauerBis = "";
        return;
      }
    } else {
      return;
    }

    schuhe.mietdauerVon = formatDate(schuhe.mietdauerVon);
    schuhe.mietdauerBis = formatDate(schuhe.mietdauerBis);
  }
}

function handleMieten(schuhe) {
    console.log("Mieten-Button wurde geklickt!");
    schuhe.mieten = !schuhe.mieten; 
    

    console.log("Ausgewählte Schuhen:", filteredSchuhen);

    const selectedSchuhen = filteredSchuhen.filter(schuhe => schuhe.mieten);

    if (selectedSchuhen.length > 0) {
        const mieterId = "6767eee93abb0a44ec56daec";
        const schuheId = selectedSchuhen.map(schuhe => schuhe.schuheId);


        axios.put(`/api/service/mietSchuhe`, {
            schuheId: schuheId,
            mieterId: mieterId,
        })
        .then(response => {
            console.log("Serverantwort:", response);
            
            if (response.status === 200) {
                const updatedSchuhe = response.data;
                const index = filteredSchuhen.findIndex(item => item.schuheId === updatedSchuhe.schuheId);
                
                if (index !== -1) {

                    filteredSchuhen[index].schuheState = updatedSchuhe.schuheState;
                }
                
                console.log('Schuhe gemietet:', updatedSchuhe);
            } else {
                throw new Error(`Fehler beim Mieten von Schuhe: ${response.status} - ${response.statusText}`);
            }
        })
        .catch(error => {
            console.error(error.message); 
        });
    } else {
        console.warn("Es wurden keine Schuhe ausgewählt.");
    }
}


  // Funktion zur Überprüfung und Konvertierung des Datums
  function validateAndFormatDate(date) {
    if (date instanceof Date) {
      return formatDate(date);
    }
    return ""; 
  }

  function calculateSelectedDays(bisDatum, vonDatum) {
    const bisDate = new Date(bisDatum);
    const vonDate = new Date(vonDatum);
    // @ts-ignore
    return Math.ceil((bisDate - vonDate) / (1000 * 60 * 60 * 24));    
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

  return bildpfad || '';
}
</script>

<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<h1 class="mt-3">Alle Damenschuhe</h1>

<!-- Filter für Verfügbarkeit -->
<label for="availability">Verfügbarkeit:</label>
<select bind:value={selectedAvailability} id="availability" class="form-select" on:change={filterSchuhen}>
  <option value="all">Alle</option>
  <option value="available">Verfügbar</option>
  <option value="rented">Vermietet</option>
</select>

<!-- Filter für Größe -->
<label for="size">Größe:</label>
<select bind:value={selectedSize} id="size" class="form-select" on:change={filterSchuhen}>
  <option value="all">Alle Größen</option>
  <option value="16">16</option>
      <option value="17">17</option>
      <option value="18">18</option>
      <option value="19">19</option>
      <option value="20">20</option>
      <option value="21">21</option>
      <option value="22">22</option>
      <option value="23">23</option>
      <option value="24">24</option>
      <option value="25">26</option>
      <option value="26">26</option>
      <option value="27">27</option>
      <option value="28">28</option>
      <option value="29">29</option>
      <option value="30">30</option>
      <option value="31">31</option>
      <option value="32">32</option>
      <option value="33">33</option>
      <option value="34">34</option>
      <option value="35">35</option>
      <option value="36">36</option>
      <option value="37">37</option>
      <option value="38">38</option>
      <option value="39">39</option>
      <option value="40">40</option>
      <option value="41">41</option>
      <option value="42">42</option>
      <option value="43">43</option>
      <option value="44">44</option>
      <option value="45">45</option>
      <option value="46">46</option>
      <option value="47">47</option>
      <option value="48">48</option>
      <option value="49">49</option>
      <option value="50">50</option>
</select>

<!-- Filter für Preissortierung -->
<label for="priceSort">Preissortierung:</label>
<select bind:value={selectedPriceSort} id="priceSort" class="form-select" on:change={filterSchuhen}>
  <option value="asc">Niedrigster zuerst</option>
  <option value="desc">Höchster zuerst</option>
</select>

<!-- Filter für Marken -->  
<label for="brand">Marke:</label>
<select bind:value={selectedBrand} id="brand" class="form-select" on:change={filterSchuhen}>
  <option value="all">Alle Marken</option>
  {#each Array.from(new Set(schuhen.map(schuhe => schuhe.marke))) as uniqueBrand}
  <option value={uniqueBrand}>{uniqueBrand}</option>
{/each}
</select>

<table class="table">
  <thead>
    <tr>
      <th scope="col">Marke</th>
      <th scope="col">Beschreibung</th>
      <th scope="col">Tagessatz</th>
      <th scope="col">Grösse</th>
      <th scope="col">Type</th>
      <th scope="col">Status</th>
      <th scope="col">Status</th>
      <th scope="col">Farbe</th>
      <th scope="col">Mietdauer Von</th>
      <th scope="col">Mietdauer Bis</th>
      <th scope="col">Anzahl Tage</th>
      <th scope="col">Total Kosten</th>
      <th scope="col">Mieten</th>
      <th scope="col">Bild</th>
    </tr>
  </thead>
  <tbody>
    {#each filteredSchuhen as schuhe (schuhe)}
      {#if schuhe.schuheType === "DAMENSCHUH"}
        <tr>
          <td>{schuhe.marke}</td>
          <td>{schuhe.schuheBeschreibung}</td>
          <td>{schuhe.preis}</td>
          <td>{schuhe.groesse}</td>
          <td>{schuhe.schuheType}</td>
          <td>{schuhe.schuheState}</td>
          <td>
            {#if schuhe.schuheState === "VERFUEGBAR"}
              <i class="fas fa-circle" style="color: green;"></i>
            {:else if schuhe.schuheState === "VERMIETET"}
              <i class="fas fa-circle" style="color: red;"></i>
            {/if}       
          </td>
          <td>{schuhe.schuheFarbe}</td>
          <td>
            {#if schuhe.schuheState !== "VERMIETET"}
            <input type="date" bind:value={schuhe.mietdauerVon} on:input={() => handleDateInput(schuhe, 'von')} />
            {/if}
          </td>
          <td>
            {#if schuhe.schuheState !== "VERMIETET"}
            <input type="date" bind:value={schuhe.mietdauerBis} on:input={() => handleDateInput(schuhe, 'bis')} />
            {/if}
          </td>
          <td>
            
              {#if schuhe.mietdauerBis && schuhe.mietdauerVon && schuhe.schuheState !== "VERMIETET"}
                {calculateSelectedDays(schuhe.mietdauerBis, schuhe.mietdauerVon)} Tage
              {/if}
            
          </td>
          <td>
            {#if schuhe.mietdauerBis && schuhe.mietdauerVon && schuhe.schuheState !== "VERMIETET"}
              {calculateSelectedDays(schuhe.mietdauerBis, schuhe.mietdauerVon) * schuhe.preis} Fr.
            {/if}
          </td>
          <td>
            {#if schuhe.schuheState === "VERMIETET"}
            <span class="badge bg-secondary">Vermietet bis {validateAndFormatDate(schuhe.mietdauerBis)}</span>
            {:else if schuhe.schuheState === "VERFUEGBAR"}
                <button
                    type="button"
                    class="btn btn-primary btn-sm"
                    on:click={() => {
                        mietSchuhe(schuhe.schuheId);
                    }}
                >
                    Mieten
                </button>
            {/if}
        </td>
          <td>
            <img src={getBildpfad(schuhe)} alt="Schuhsbild" class="damenschuh" />
          </td>
        </tr>
      {/if}
    {/each}
  </tbody>
</table>
<style>
  .damenschuh {
    object-fit: cover;
    height: 50px;
    
  }

  
</style>