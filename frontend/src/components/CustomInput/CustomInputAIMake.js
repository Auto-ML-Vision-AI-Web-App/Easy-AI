import React from 'react';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: '25ch',
        },
    },
}));

export default function InputAIMake({label, defaultValue, helperText}) {
    const classes = useStyles();

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <Grid container spacing={2}>
                <Grid item xs={5}>
                <TextField
                    id="outlined-helperText"
                    label={label}
                    defaultValue={defaultValue}
                    variant="outlined"
                />
                </Grid>

                <Grid item xs={6}>
                <p style={{color:'gray'}}><i>{helperText}</i></p>
                </Grid>

            </Grid>
        </form>
    );
}
