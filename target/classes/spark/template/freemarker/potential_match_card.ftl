<#assign card>
  <div id = "potential_card">
    <div id = "card_content">
      <div id = "left-content">
        <div id = "name_info">
          <h1>${name}</h1>
          <h3>${class_info}</h3>
          <h3>${concentration}</h3>
        </div>
          <div id = "top_interests">
            <h1>${top_interests}</h1>
              <ul id = "top_interests_list"></ul>
          </div>
      </div>
      <div id = "right-content">
        <img id = "match_image" src=${image_url}>
      </div>
    </div>
  </div>
</#assign>