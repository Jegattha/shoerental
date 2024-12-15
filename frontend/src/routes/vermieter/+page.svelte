<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
    import { jwt_token } from "../../store";
  
    const api_root = $page.url.origin;

    let currentPage;
let nrOfPages = 0;
let defaultPageSize = 4;
  
    let vermieters = [];
    let vermieter = {
      vermieterId: null,
      name: null,
      email: null,
    };

    $: {
if ($jwt_token !== "") {
let searchParams = $page.url.searchParams;
if (searchParams.has("page")) {
currentPage = searchParams.get("page");
} else {
currentPage = "1";
}
getVermieters();
}
}
  
   /* onMount(() => {
      getVermieters();
    }); */
  
    function getVermieters() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
      var config = {
        method: "get",
        url: api_root + "/api/vermieter" + query,
        headers: {Authorization: "Bearer "+$jwt_token},
      };
  
      axios(config)
        .then(function (response) {
          vermieters = response.data.content;
          nrOfPages = response.data.totalPages;
        })
        .catch(function (error) {
          alert("Could not get vermieters");
          console.log(error);
        });
    }

    function validateEmailAndcreateVermieter(){
 var config = {
 method: "get",
 url: "https://disify.com/api/email/" + vermieter.email
 };
 axios(config)
 .then(function (response) {
 console.log("Validated email "+vermieter.email);
 console.log(response.data);
 if(response.data.format && !response.data.disposable
 && response.data.dns
 ){
  createVermieters();
 }else{
 alert("Email "+vermieter.email+" is not valid.");
 }
 })
 .catch(function (error) {
 alert("Could not validate email");
 console.log(error);
 });
}

  
    function createVermieters() { 
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
        function deleteVermieter(vermieterId) {
      var config = {
        method: "delete",
        url: `${api_root}/api/vermieter/delete/${vermieterId}`,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
        },
      };
  
      axios(config)
        .then(function (response) {
          alert("Vermieter deleted");
          getVermieters();
        })
        .catch(function (error) {
          alert("Could not delete Vermieter");
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
    <button type="button" class="btn btn-primary" on:click={createVermieters}
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
  <nav>
    <ul class="pagination">
    {#each Array(nrOfPages) as _, i}
    <li class="page-item">
    <a
    class="page-link"
    class:active={currentPage == i + 1}
    href={"/vermieter?page=" + (i + 1)}>{i + 1}
    </a>
    </li>
    {/each}
    </ul>
    </nav>