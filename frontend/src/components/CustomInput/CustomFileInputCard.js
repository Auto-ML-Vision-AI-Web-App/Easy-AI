import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';

import EditIcon from '@material-ui/icons/Edit';

const useStyles = makeStyles({
  root: {
    maxWidth: 345,
    height: 150,
    margin: 10
  },
});

export default function MediaCard(props) {
  const classes = useStyles();

  const classChange = (e) => {
    props.onChange(e.target.name, e.target.value);
  };

  return (
    <Card className={classes.root}>
      <CardActionArea>
        <CardContent>
          <Grid container justifyContent="center" alignItems="center">
            <Grid item xs={9}>
              <Typography gutterBottom variant="h5" component="h2">
                <TextField
                  id="outlined-name"
                  name={props.dataClass}
                  label={props.dataClass}
                  onChange={classChange}
                  variant="outlined"
                />
              </Typography>
            </Grid>
              <Typography variant="body2" color="textSecondary" component="p">
                전체를 클릭하여 데이터를 업로드하세요
              </Typography>
          </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}
