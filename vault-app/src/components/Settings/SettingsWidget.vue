<template>
    <div class="parent-widget">
        <div class="card">
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
                    <p for="team-name">Team Name:</p>
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
                    <p for="new-address">Add Address:</p>
                    <input v-model="newAddress" id="new-address" type="text" />
                    <button @click="addAddress">Add</button>
                </div>

                <ul>
                    <li v-for="(address, index) in addresses" :key="index">
                        {{ address }} <button @click="removeAddress(index)">Remove</button>
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
}

.card{
    text-align: left;
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
    background-color: #282c34;

}

.profile-pic {
    width: 100%;
    height: auto;
    display: block;
}

.settings-container {
    font-family: monospace;
    max-width: 600px;
    margin: auto;
    padding: 20px;
    border-radius: 8px;
    background-color: #20232a;
    color: whitesmoke;
}

.section {
    margin-bottom: 20px;
    min-width: 300px;
}

h2 {
    margin-bottom: 10px;
    color: whitesmoke;
    border-bottom: 2px solid #61dafb;
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
    background-color: #282c34;
    color: #61dafb;
}

button {
    padding: 8px 15px;
    border: none;
    background-color: #61dafb;
    color: #20232a;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 4px;
}

button:hover {
    background-color: #20232a;
    color: #61dafb;
    border: 1px solid #61dafb;
}

ul {
    list-style: none;
    padding: 0;
}

li {
    background-color: #32363a;
    padding: 10px;
    margin-bottom: 8px;
    border-radius: 4px;
}
</style>
  