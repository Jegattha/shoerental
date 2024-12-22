<script>
    import axios from "axios";
    import { isAuthenticated, user, jwt_token } from "../../store";
    import { page } from "$app/stores";
  
    const api_root = $page.url.origin;
  
    let schuhen = [];
    let vermieteteSchuhe = [];
  
  $: {
    if ($jwt_token !== "") {
        getData();
    }
  }
  
  function getData() {
    // Gemietete Schuhe (für Kunden)
    axios
        .get(api_root + "/api/schuhe", {
            headers: {
                Authorization: "Bearer " + $jwt_token,
            },
        })
        .then(function (response) {
            schuhen = response.data;
            console.log("Gemietete Schuhe:", schuhen);
        })
        .catch(function (error) {
            alert("Could not get rented shoe data");
            console.log(error);
        });
  
   
    }
  
  
  function formatiereDatum(datum) {
    const options = { day: "2-digit", month: "2-digit", year: "numeric" };
    return new Date(datum).toLocaleDateString("de-DE", options);
  }
  </script>
  
  <h1>Account Details</h1>
  
  {#if $isAuthenticated}
    <p><img src={$user.picture} alt="" srcset="" /></p>
    <p><b>Name:</b> {$user.name}</p>
    <p><b>Nickname:</b> {$user.nickname}</p>
    <p><b>First Name:</b> {$user.given_name}</p>
    <p><b>Last Name:</b> {$user.family_name}</p>
    <p><b>Email:</b> {$user.email}</p>
  
  
    {#if $user.user_roles && $user.user_roles.length > 0}
        <p><b>Roles:</b> {$user.user_roles}</p>
    {/if}
  {:else}
    <p>Not logged in</p>
  {/if}
  
  <!-- Gemietete Schuhe für Kunden -->
  {#if $isAuthenticated && (!$user.user_roles || (!$user.user_roles.includes("admin") && !$user.user_roles.includes("vermieter"))) && schuhen.length > 0}
    <div class="rented-shoes">
        <h2>Gemietete Schuhe</h2>   
        <ul>
            {#each schuhen as schuhe}
                {#if schuhe.schuheState === "VERMIETET"}
                    <li class="bg-light p-3">
                        <p><b>Schuhe ID:</b> {schuhe.schuheId}</p>
                        <p><b>Marke:</b> {schuhe.marke}</p>
                        <p><b>Gemietet bis:</b> {formatiereDatum(schuhe.mietdauerBis)}</p>
                    </li>
                {/if}
            {/each}
        </ul>
    </div>
  {/if}
  
  <style>
  h1, h2 {
    text-align: center;
    margin-top: 30px;
  }
  
  </style>
       