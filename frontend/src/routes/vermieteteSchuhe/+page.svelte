<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { jwt_token } from "../../store";

  const api_root = $page.url.origin;

  let currentPage;
  let nrOfPages = 0;
  let defaultPageSize = 15;

  let schuhen = [];
  let mieters = [];

  $: {
    if ($jwt_token !== "") {
      let searchParams = $page.url.searchParams;

      if (searchParams.has("page")) {
        currentPage = searchParams.get("page");
      } else {
        currentPage = "1";
      }
      getData();
    }
  }

  function getData() {
    let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;

    // Fetch schuhe und mieters
    Promise.all([
      axios.get(api_root + "/api/schuhe" + query, {
        headers: {
          Authorization: "Bearer " + $jwt_token,
        },
      }),
      axios.get(api_root + "/api/mieter" + query, {
        headers: {
          Authorization: "Bearer " + $jwt_token,
        },
      }),
    ])
      .then(function (responses) {
        schuhen = responses[0].data;
        mieters = responses[1].data.content;
        nrOfPages = responses[1].data.totalPages;

        console.log("Schuhen:", schuhen);
        console.log("Mieters:", mieters);
      })
      .catch(function (error) {
        alert("Could not get data");
        console.log(error);
      });
  }
  function getSchuhen() {
  var config = {
    method: "get",
    url: api_root + "/api/schuhe",
    headers: {Authorization: "Bearer "+$jwt_token},
  };

  axios(config)
    .then(function (response) {
      schuhen = response.data;
    })
    .catch(function (error) {
      alert("Konnte keine Schuhe abrufen");
      console.log(error);
    });
}
getSchuhen();
  
  function schuheAvailable(schuheId) {
      let schuhe = {
      schuheId: schuheId,
      };
      var config = {
          method: "post",
          url: api_root + "/api/service/availableSchuhe",
          headers: {Authorization: "Bearer "+$jwt_token,
              "Content-Type": "application/json",},
          data: schuhe,
  };
  axios(config)
          .then(function (response) {
              getSchuhen();
              getData();
          })
          .catch(function (error) {
              alert("Schuhe konnte nicht freigegeben werden");
              console.log(error);
          });
  }

  function formatiereDatum(datum) {
  const options = { day: "2-digit", month: "2-digit", year: "numeric" };
  return new Date(datum).toLocaleDateString("de-DE", options);
}
</script>

<br>
<h1>All Data</h1>
<table class="table">
  <thead>
    <tr>
      <th scope="col">Schuhe ID</th>
      <th scope="col">Marke</th>
      <th scope="col">Mieter ID</th>
      <th scope="col">Name</th>
      <th scope="col">Vermietet bis</th>
      <th scope="col">Aktion</th>    
    </tr>
  </thead>
  <tbody>
    {#each schuhen as schuhe}
      {#if schuhe.schuheState === "VERMIETET" && schuhe.mieterId}
        {#each mieters as mieter}
          {#if mieter.mieterId === schuhe.mieterId}
            <tr>
              <td>{schuhe.schuheId}</td>
              <td>{schuhe.marke}</td>
              <td>{schuhe.mieterId}</td>
              <td>{mieter.name}</td>
              <td>
                  {#if schuhe.schuheState === "VERMIETET" && schuhe.mieterId && schuhe.mietdauerBis}
                {formatiereDatum(schuhe.mietdauerBis)}
              {/if}
              </td>
              <td>
                  {#if schuhe.schuheState === "VERMIETET"}
                  <button
                          type="button"
                          class="btn btn-primary btn-sm"
                          on:click={() => {
                              schuheAvailable(schuhe.schuheId);
                          }}
                      >
                          Freigeben      
                      </button>
                  {/if}
              </td>
            </tr>
          {/if}
        {/each}
      {/if}
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
          href={"/vermieteteSchuhe?page=" + (i + 1)}
          >{i + 1}
        </a>
      </li>
    {/each}
  </ul>
</nav>
