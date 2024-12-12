<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
    import { jwt_token } from "../../store";
  
    const api_root = $page.url.origin;
  
    let vermieters = [];
    let vermieter = {
      name: null,
      email: null,
    };
  
    onMount(() => {
      getVermieters();
    });
  
    function getVermieters() {
      var config = {
        method: "get",
        url: api_root + "/api/vermieter",
        headers: {Authorization: "Bearer "+$jwt_token},
      };
  
      axios(config)
        .then(function (response) {
          vermieters = response.data;
        })
        .catch(function (error) {
          alert("Could not get vermieters");
          console.log(error);
        });
    }
  
    function createVermieter() {
      var config = {
        method: "post",
        url: api_root + "/api/vermieter",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
        },
        data: vermieter,
      };
  
      axios(config)
        .then(function (response) {
          alert("Vermieter created");
          getVermieters();
        })
        .catch(function (error) {
          alert("Could not create Vermieter");
          console.log(error);
        });
    }
  </script>
  
  <h1 class="mt-3">Create Vermieter</h1>
  <form class="mb-5">
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="name">Name</label>
        <input
          bind:value={vermieter.name}
          class="form-control"
          id="name"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="email">E-Mail</label>
        <input
          bind:value={vermieter.email}
          class="form-control"
          id="email"
          type="email"
        />
      </div>
    </div>
    <button type="button" class="btn btn-primary" on:click={createVermieter}
      >Submit</button
    >
  </form>
  
  <h1>All Vermieters</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">Name</th>
        <th scope="col">E-Mail</th>
        <th scope="col">ID</th>
      </tr>
    </thead>
    <tbody>
      {#each vermieters as vermieter}
        <tr>
          <td>{vermieter.name}</td>
          <td>{vermieter.email}</td>
          <td>{vermieter.id}</td>
        </tr>
      {/each}
    </tbody>
  </table>
  