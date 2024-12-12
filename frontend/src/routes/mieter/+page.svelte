<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
  
    const api_root = $page.url.origin;
    

  
    let mieters = [];
    let mieter = {
      mieterId: null,
      name: null,
      email: null,
    };
  
    onMount(() => {
      getMieters();
    });
  

    function getMieters() {
      var config = {
        method: "get",
        url: api_root + "/api/mieter",
        headers: {},
      };
  
    axios(config)
        .then(function (response) {
          mieters = response.data;
        })
        .catch(function (error) {
          alert("Could not get mieters");
          console.log(error);
        });
    }
    //getMieters();
  
   
  
    function createMieters() {
      var config = {
        method: "post",
        url: api_root + "/api/mieter",
        headers: {
          "Content-Type": "application/json",
        },
        data: mieter,
      };
  
      axios(config)
        .then(function (response) {
          alert("Mieter created");
          getMieters();
        })
        .catch(function (error) {
          alert("Could not create Mieter");
          console.log(error);
        });
    }
    function deleteMieter(mieterId) {
      var config = {
        method: "delete",
        url: `${api_root}/api/mieter/delete/${mieterId}`,
        headers: {
          "Content-Type": "application/json",
        },
      };
  
      axios(config)
        .then(function (response) {
          alert("Mieter deleted");
          getMieters();
        })
        .catch(function (error) {
          alert("Could not delete Mieter");
          console.log(error);
        });
    }
  
  </script>
  
  <h1 class="mt-3">Mieter erstellen</h1>
  <form class="mb-5">
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="name">Name</label>
        <input
          bind:value={mieter.name}
          class="form-control"
          id="name"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="email">Email</label>
        <input
          bind:value={mieter.email}
          class="form-control"
          id="email"
          type="email"
        />
      </div>
    </div>
    <button type="button" class="btn btn-primary" on:click={createMieters}
      >Submit</button
    >
  </form>
  
  <h1>All Mieter</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">E-Mail</th>
        <th scope="col">Aktion</th>
      </tr>
    </thead>
    <tbody>
      {#each mieters as mieter}
        <tr>
          <td>{mieter.name}</td>
          <td>{mieter.email}</td>
          <td>
            <button
              type="button"
              class="btn btn-danger"
              on:click={() => deleteMieter(mieter.mieterId)}
            >
              Delete
            </button>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
 