<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { onMount } from "svelte";
  import { jwt_token, user } from "../../store";

  const api_root = $page.url.origin;

 

  let schuhen = [];
  let schuhe = {
    schuheId: null,
    marke: null,
    preis: null,
    schuheType: null,
    groesse: null,
    schuheFarbe: null,
    schuheBeschreibung: null,
    vermieterId: null,
  };



  onMount(() => {
    getSchuhen();
  });

  


  let fehlermeldung = null;
  let filterType = "";

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
  $: filteredSchuhen = schuhen.filter(s => filterType === "" || filterType === s.schuheType);

  function getSchuhen() {
    var config = {
      method: "get",
      url: api_root + "/api/schuhe",
      headers: {Authorization: "Bearer "+$jwt_token},
    };
//jobs = schuhen, job = schuhe
    axios(config)
      .then(function (response) {
        schuhen = response.data;
      })
      .catch(function (error) {
        alert("Schuhe konnten nicht aufgerufen werden");
        console.log(error);
      });
  }
getSchuhen();

function validateForm() {
    if (
      schuhe.marke &&
      schuhe.preis &&
      schuhe.schuheType &&
      schuhe.groesse &&
      schuhe.schuheFarbe &&
      schuhe.schuheBeschreibung &&
      schuhe.vermieterId
    ) {
      return true;
    } else {
      fehlermeldung = "Bitte füllen Sie alle Felder aus.";
      return false;
    }
  }

  function getBildpfad(schuhe) {
    const schuheType = schuhe.schuheType ? schuhe.schuheType.toUpperCase() : '';
    const schuheFarbe = schuhe.schuheFarbe ? schuhe.schuheFarbe.toLowerCase() : '';

    const bildpfad = bildpfade[schuheFarbe] && bildpfade[schuheFarbe][schuheType];
    console.log("schuheType:", schuheType);
    console.log("schuheFarbe:", schuheFarbe);
    console.log("bildpfad:", bildpfad);

    return bildpfad || ''; //absoluten Pfad
  }


  function deleteSchuhe(schuheId) {
    var config = {
      method: "delete",
      url: `${api_root}/api/schuhe/delete/${schuheId}`,
      headers: {Authorization: "Bearer "+$jwt_token},
    };

    axios(config)
      .then(function (response) {
        alert("Schuhe gelöscht");
        getSchuhen();
      })
      .catch(function (error) {
        alert("Schuhe konnte nicht gelöscht werden");
        console.log(error);
      });
  }

 

    function createSchuhe() {
   let vermieterId = "675f619ab1d05c6871b190cd"; 
    if (validateForm()) {
      var config = {
        method: "post",
        url: `${api_root}/api/schuhe?vermieterId=${vermieterId}`,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
        },
        data: schuhe,
      };

      axios(config)
        .then(function (response) {
          alert("Schuhe erstellt");
          getSchuhen();
        })
        .catch(function (error) {
          alert("Schuhe konnte nicht erstellt werden");
          console.log(error);
        });
    }
  }

  function updateSchuh() {
    if (validateForm()) {
      schuhe.vermieterId = "675f619ab1d05c6871b190cd";
      var config = {
        method: "put",
        url: `${api_root}/api/schuhe/update/${schuhe.schuheId}`,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
          
        },
        data: schuhe,
      };

      axios(config)
        .then(function (response) {
          alert("Schuhe aktualisiert");
          getSchuhen();
          resetForm();
        })
        .catch(function (error) {
          alert("Schuhe konnte nicht aktualisiert werden");
          console.log(error);
        });
    }
  }

  function saveSchuhe() {
    if (validateForm()) {
      var config = {
        method: "put",
        url: `${api_root}/api/schuhe/update/${schuhe.schuheId}`,
        headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer "+$jwt_token,
      },
        data: schuhe,
      };

      axios(config)
        .then(function (response) {
          alert("Schuhe gespeichert");
          getSchuhen();
          resetForm();
        })
        .catch(function (error) {
          alert("Schuhe konnte nicht gespeichert werden");
          console.log(error);
        });
    }
  }
  function resetForm() {
    schuhe = {
      schuheId: null,
      marke: null,
      preis: null,
      schuheType: null,
      groesse: null,
      schuheFarbe: null,
      schuheBeschreibung: null,
      vermieterId: null,
    };
  }

  function editSchuhe(selectedSchuhe) {
    try {
      schuhe = { ...selectedSchuhe };
    } catch (error) {
      console.error("Fehler in editSchuhe:", error);
    }
  }

  function isSchuheVermietet(schuhe) {
    return schuhe.schuheState === "VERMIETET";
  }

  function handleUpdate(schuhe) {
    if (!isSchuheVermietet(schuhe)) {
      
      console.log("Update-Klick");
    } else {
      alert("Schuhe ist vermietet und kann nicht aktualisiert werden.");
    }
  }

  function handleDelete(schuhe) {
    if (!isSchuheVermietet(schuhe)) {
      
      console.log("Löschen-Klick");
    } else {
      alert("Schuhe ist vermietet und kann nicht gelöscht werden.");
    }
  }

</script>
{#if $user.user_roles && $user.user_roles.length > 0}
<h1 class="mt-3">Schuhe erstellen</h1>
<form class="mb-5">
  {#if fehlermeldung}
    <div class="alert alert-danger">{fehlermeldung}</div>
  {/if}
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="marke">Marke</label>
      <input
        bind:value={schuhe.marke}
        class="form-control"
        id="marke"
        type="text"
      />
    </div>
    <div class="col">
      <label class="form-label" for="type">Typ</label>
      <select bind:value={schuhe.schuheType} class="form-select" id="type">
        <option value="FRAUENSCHUH">FRAUENSCHUH</option>
        <option value="MAENNERSCHUH">MAENNERSCHUH</option>
      </select>
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="preis">Tagessatz in Franken</label>
      <input
        bind:value={schuhe.preis}
        class="form-control"
        id="preis"
        type="number"
      />
    </div>
    <div class="col">
      <label class="form-label" for="groesse">Größe</label>
      <select bind:value={schuhe.groesse} class="form-select" id="groesse">
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
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="schuheFarbe">Schuhfarbe</label>
      <select
        bind:value={schuhe.schuheFarbe}
        class="form-control"
        id="schuheFarbe">
      
        <option value="blau">Blau</option>
        <option value="gruen">Grün</option>
        <option value="schwarz">Schwarz</option>
        <option value="weiss">Weiss</option>
        <option value="grau">Grau</option>
        <option value="braun">Braun</option>
        <option value="rot">Rot</option>
      </select>
    </div>
    <div class="col">
      <label class="form-label" for="schuheBeschreibung">Schuhbeschreibung</label>
      <input
        bind:value={schuhe.schuheBeschreibung}
        class="form-control"
        id="schuheBeschreibung"
        type="text"
      />
    </div>

    <div class="col">
      <label class="form-label" for="vermieterId">Vermieter Id</label>
      <input
        bind:value={schuhe.vermieterId}
        class="form-control"
        id="vermieterId"
        type="text"
      />
    </div>

  </div>
  <button type="button" class="btn btn-primary" on:click={createSchuhe}>
    Absenden
  </button>
  <button class="btn btn-success" on:click={saveSchuhe}>
    Update Speichern
  </button>
</form>
{/if}

<h1>Alle Schuhe</h1>
<select bind:value={filterType} class="form-select">
  <option value="">Alle</option>
  <option value="FRAUENSCHUH">Frauenschuh</option>
  <option value="MAENNERSCHUH">Maennerschuh</option>
</select>
<br>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Bild</th>
      <th scope="col">ID</th>
      <th scope="col">Marke</th>
      <th scope="col">Typ</th>
      <th scope="col">Tagessatz </th>
      <th scope="col">Grösse</th>
      <th scope="col">Farbe</th>

  <!-- <th scope="col">Schuhbeschreibung</th> (isch en kommentar!!)-->

      <th scope="col">Schuhbeschreibung</th>
      <th scope="col">Aktionen</th>
    </tr>
  </thead>
  <tbody>
    {#each schuhen as schuhe}
      {#if filterType === "" || filterType === schuhe.schuheType}
        <tr>
          <td>
            <img src={getBildpfad(schuhe)} alt="Schuhbild" class="frauenschuh-bild img-fluid" />
          </td>
        <td>{schuhe.schuheId}</td>
          <td>{schuhe.marke}</td>
          <td>{schuhe.schuheType}</td>
          <td>{schuhe.preis}</td>
          <td>{schuhe.groesse}</td>
          <td>{schuhe.schuheFarbe}</td>
          <td>{schuhe.schuheBeschreibung}</td>
        

         <!-- <td>{schuhe.schuheBeschreibung}</td> (isch en KOMMENTAR!!) -->

          <td>
            {#if isSchuheVermietet(schuhe)}
              <span class="badge bg-secondary">Aktuell vermietet</span>
            {:else}
              <button class="btn btn-danger" on:click={() => deleteSchuhe(schuhe.schuheId)}>
                Löschen
              </button>
              <button class="btn btn-primary" on:click={() => editSchuhe(schuhe)}>
                Update
              </button>
            {/if}
          </td>
        </tr>
      {/if}
    {/each}
  </tbody>
</table>



<style>
  .frauenschuh-bild {
    width: 100%; 
    height: auto; 
   
    
  }

  
</style>



<!-- ALT GEMàSS èBUNGSAUFGABe 

<h1 class="mt-3">Schuhe erfassen</h1>  
<form class="mb-5">
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="description">Beschreibung</label>
      <input
        bind:value={schuhe.schuheBeschreibung}
        class="form-control"
        id="description"
        type="text"
      />
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="type">Schuhe Type</label>
      <select bind:value={schuhe.schuheType} class="form-select" id="type">
        <option value="FRAUENSCHUH">FRAUENSCHUH</option>
        <option value="MAENNERSCHUH">MAENNERSCHUH</option>
      </select>
    </div>
    <div class="col">
      <label class="form-label" for="earnings">Ea</label>
      <input
        bind:value={job.earnings}
        class="form-control"
        id="earnings"
        type="number"
      />
    </div>
    <div class="col">
      <label class="form-label" for="companyid">Company ID</label>
      <input
        bind:value={job.companyId}
        class="form-control"
        id="companyid"
        type="text"
      />
    </div>
  </div>
  <button type="button" class="btn btn-primary" on:click={createJob}
    >Submit</button
  >
</form>

<h1>All Jobs</h1>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Description</th>
      <th scope="col">Type</th>
      <th scope="col">Earnings</th>
      <th scope="col">State</th>
      <th scope="col">CompanyId</th>
      <th scope="col">FreelancerId</th>
    </tr>
  </thead>
  <tbody>
    {#each jobs as job}
      <tr>
        <td>{job.description}</td>
        <td>{job.jobType}</td>
        <td>{job.earnings}</td>
        <td>{job.jobState}</td>
        <td>{job.companyId}</td>
        <td>{job.freelancerId}</td>
      </tr>
    {/each}
  </tbody>
</table>

-->