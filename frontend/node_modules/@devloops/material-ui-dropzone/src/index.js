import React from "react";
import { Grid, Button } from "@material-ui/core";
import DropzoneArea from "./Components/DropzoneArea";
import DropzoneCard from "./Components/DropzoneCard";
import FileButton from "./Components/FileButton";
let func = () => {};
const App = () => {
  return (
    <>
      <br />
      <br />
      <Grid container spacing={4} justify="center">
        <Grid item sm={6}>
          <DropzoneArea inside={false} />
        </Grid>
        <Grid item sm={6}>
          <DropzoneArea />
        </Grid>
        <Grid item sm={4}>
          <DropzoneCard />
        </Grid>
        <Grid item sm={4}>
          <FileButton onError={err => console.log(err)} />
          <FileButton ButtonComponent={Button} fabProps={{ variant: "contained" }} />
        </Grid>
      </Grid>
      <br />
      <br />
    </>
  );
};

export default App;
