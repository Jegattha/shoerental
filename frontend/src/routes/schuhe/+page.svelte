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
   let vermieterId = "6767f01c3abb0a44ec56daed"; 
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
      schuhe.vermieterId = "6767f01c3abb0a44ec56daed";
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
        <option value="DAMENSCHUH">DAMENSCHUHE</option>
        <option value="HERRENSCHUH">HERRENSCHUHE</option>
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
  <option value="DAMENSCHUH">Damenschuhe</option>
  <option value="HERRENSCHUH">Herrenschuhe</option>
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
            <img src={getBildpfad(schuhe)} alt="Schuhbild" class="damenschuh-bild img-fluid" />
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
  .damenschuh-bild {
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