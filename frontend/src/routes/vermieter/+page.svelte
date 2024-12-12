<script>
    import axios from "axios";
    import { page } from "$app/stores";
  import { onMount } from "svelte";

  const api_root = $page.url.origin;

   

    let vermieters = [];
    let vermieter = {
        name: null,
        email: null,
    };

    onMount(() => {
    getCompanies();
  });

    


  function getVermieters() {
    var config = {
      method: "get",
      url: api_root + "/api/vermieter",
      headers: {},
    };

    axios(config)
      .then(function (response) {
        companies = response.data;
      })
      .catch(function (error) {
        alert("Could not get vermieters");
        console.log(error);
      });
  }

   // getVermieters();

   

    function createVermieters() {
    
            var config = {
                method: "post",
                url: api_root + "/api/vermieter",
                headers: {
                    "Content-Type": "application/json",
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
            <input bind:value={vermieter.name} class="form-control" id="name" type="text" />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="email">Email</label>
            <input bind:value={vermieter.email} class="form-control" id="email" type="email" />
        </div>
    </div>
    <button type="button" class="btn btn-primary" on:click={createVermieters}>Submit</button>
</form>

<h1>All Vermieter</h1>
<table class="table">
    <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">E-Mail</th>
            <th scope="col">Name</th>
        </tr>
    </thead>
    <tbody>
        {#each vermieters as vermieter}
        <tr>
            <td>{vermieter.vermieterId}</td>
            <td>{vermieter.name}</td>
            <td>{vermieter.email}</td>
        </tr>
        {/each}
    </tbody>
</table>
