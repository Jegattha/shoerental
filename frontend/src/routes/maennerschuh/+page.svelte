<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { onMount } from "svelte";
  import { isAuthenticated, jwt_token, user} from "../../store";

 
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

    
      const mieterId = "675db76bb8c9d3496ce577c1";
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

    // Formatiere das Datum auf 'YYYY-MM-DD'
    schuhe.mietdauerVon = formatDate(schuhe.mietdauerVon);
    schuhe.mietdauerBis = formatDate(schuhe.mietdauerBis);
  }
}


function handleMieten(schuhe) {
    console.log("Mieten-Button wurde geklickt!");
    schuhe.mieten = !schuhe.mieten; 
    

    const selectedSchuhen = filteredSchuhen.filter(schuhe => schuhe.mieten);

   
    if (selectedSchuhen.length > 0) {
  const mieterId = "675db76bb8c9d3496ce577c1";
const schuheId = selectedSchuhen.map(schuhe => schuhe.schuheId);


        console.log("Schuhe IDs:", schuheId);

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

  return bildpfad || '';
}


</script>
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<h1 class="mt-3">Alle Männerschuhe</h1>

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
      <th scope="col">Total Kosten </th>
      <th scope="col">Mieten</th>
      <th scope="col">Bild</th>
    </tr>
  </thead>
  <tbody>
    {#each filteredSchuhen as schuhe (schuhe)}
      {#if schuhe.schuheType === "MAENNERSCHUH"}
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
            <img src={getBildpfad(schuhe)} alt="Schuhsbild" class="maennerschuh" />
          </td>
        </tr>
      {/if}
    {/each}
  </tbody>
</table>
<style>
  .maennerschuh {
    object-fit: cover;
    height: 200px;
    
  }

  
</style>