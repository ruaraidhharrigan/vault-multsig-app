<template>
    <div class="parent-widget">
            <div class="section team-settings">
                <h2>Account</h2>
                <div class="input-group centered-group">
                    <p for="profile-picture" class="input-label">Profile Picture:</p>
                    <div class="input-field center-content">
                        <div class="profile-pic-container center-content">
                            <img :src="profilePictureDataUrl" alt="Profile Picture" class="profile-pic" />
                        </div>
                        <input @change="onFileChange" id="profile-picture" type="file" />
                    </div>
                </div>
                <div class="input-group">
                    <p class="teamname" for="team-name">Team Name:</p>
                    <input v-model="teamName" id="team-name" type="text" />
                </div>
                <div class="input-group">
                    <label for="description">Description:</label>
                    <textarea v-model="description" id="description"></textarea>
                </div>
            </div>

            <!-- Address Management -->
            <div class="section address-management">
                <h2>Address Management</h2>
                <div class="input-group centered group">
                    <p class="teamname" for="new-address">Add Address:</p>
                    <input v-model="newAddress" id="new-address" type="text" />
                    <button class="style-button" @click="addAddress">Add</button>
                </div>

                <ul>
                    <li v-for="(address, index) in addresses" :key="index">
                        {{ address }} <button class="style-button" @click="removeAddress(index)">Remove</button>
                    </li>
                </ul>
            </div>

            <!-- Threshold Settings -->
            <div class="section threshold-settings">
                <h2>Threshold Settings</h2>
                <div class="input-group">
                    <p for="threshold">Threshold: {{ threshold }}</p>
                    <input v-model="threshold" id="threshold" type="range" min="1" max="10" />
                </div>
            </div>
            <button class="style-button">Save</button>
        </div>
</template>
  
<script>
export default {
    data() {
        return {
            teamName: '',
            profilePictureDataUrl: 'https://localhost:8080/pfp.png', // Placeholder image URL
            description: '',
            newAddress: '',
            addresses: [],
            threshold: 5,
        };
    },
    methods: {
        onFileChange(e) {
            const file = e.target.files[0];
            const reader = new FileReader();
            reader.onload = (e) => {
                this.profilePictureDataUrl = e.target.result;
            };
            reader.readAsDataURL(file);
        },
        addAddress() {
            if (this.newAddress) {
                this.addresses.push(this.newAddress);
                this.newAddress = '';
            }
        },
        removeAddress(index) {
            this.addresses.splice(index, 1);
        },
    },
};
</script>
  
<style scoped>
h2 {
    text-align: center;
    color:black;
}

.card{
    text-align: left;
}

.teamname{
    color:black;
}

input{
    background-color: whitesmoke;
}

.parent-widget {
  display: flex;
  flex-direction: column;
  align-items: left;
  text-align: left;
  background-color: #f6f8fa;
  border-radius: 10px;
  padding: 20px;
  margin-left: 300px;
  width: 55%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}


.profile-pic-container {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 150px;
    height: 150px;
    margin-bottom: 20px;
    overflow: hidden;
    border-radius: 50%;
    background-color: white;

}

.profile-pic {
    width: 100%;
    height: auto;
    display: block;
}
textarea{
    background-color: whitesmoke;
    color: black;

}

.settings-container {
    font-family: monospace;
    max-width: 600px;
    margin: auto;
    padding: 20px;
    border-radius: 8px;
    background-color: white;
    color: black;
}

.section {
    margin-bottom: 20px;
    min-width: 300px;
}

h2 {
    margin-bottom: 10px;
    color: black;
    border-bottom: 2px solid black;
    padding-bottom: 5px;
    font-size:20px;
}


.input-group {
    margin-bottom: 15px;
}

.centered-group {
    display: flex;
    flex-direction: column;
}

.input-label {
    margin-bottom: 5px;
    font-size: 14px;
}


.input-field {
    width: 100%;
    display: flex;
    flex-direction: column;


}

input[type="file"] {
    display: inline-block;
}

label {
    display: block;
    margin-bottom: 5px;
    font-size: 14px;
}



input[type="text"],
textarea input[type="range"] {
    width: 70%;
    padding: 8px;
    margin-bottom: 10px;
    border-radius: 4px;
    border: 1px solid #32363a;
    background-color: whitesmoke;
    color: black;
}



ul {
    list-style: none;
    padding: 0;
}

li {
    background-color: whitesmoke;
    padding: 10px;
    margin-bottom: 8px;
    border-radius: 4px;
}
</style>
  