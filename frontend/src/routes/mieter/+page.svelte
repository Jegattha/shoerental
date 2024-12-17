<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
    import { jwt_token } from "../../store";

    const api_root = $page.url.origin;
    
    let currentPage;
let nrOfPages = 0;
let defaultPageSize = 4;
  
    let mieters = [];
    let mieter = {
      mieterId: null,
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
getMieters();
}
}
  
   /* onMount(() => {
      getMieters();
    }); */
  

    function getMieters() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
      var config = {
        method: "get",
        url: api_root + "/api/mieter" + query,
        headers: {Authorization: "Bearer "+$jwt_token},
      };
  
    axios(config)
        .then(function (response) {
          mieters = response.data.content;

          nrOfPages = response.data.totalPages;
        })
        .catch(function (error) {
          alert("Could not get mieters");
          console.log(error);
        });
    }
    //getMieters();
  
    function validateEmailAndcreateMieter(){
 var config = {
 method: "get",
 url: "https://disify.com/api/email/" + mieter.email
 };
 axios(config)
 .then(function (response) {
 console.log("Validated email "+mieter.email);
 console.log(response.data);
 if(response.data.format && !response.data.disposable
 && response.data.dns
 ){
  createMieters();
 }else{
 alert("Email "+mieter.email+" is not valid.");
 }
 })
 .catch(function (error) {
 alert("Could not validate email");
 console.log(error);
 });
}

  
    function createMieters() {
      var config = {
        method: "post",
        url: api_root + "/api/mieter",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer "+$jwt_token,
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
          Authorization: "Bearer "+$jwt_token,
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
    <button type="button" class="btn btn-primary" on:click={validateEmailAndcreateMieter}
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
  <nav>
    <ul class="pagination">
    {#each Array(nrOfPages) as _, i}
    <li class="page-item">
    <a
    class="page-link"
    class:active={currentPage == i + 1}
    href={"/mieter?page=" + (i + 1)}>{i + 1}   
    </a>
    </li>
    {/each}
    </ul>
    </nav>