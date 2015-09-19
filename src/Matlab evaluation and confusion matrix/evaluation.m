clear all;
close all;
clc;

load validation_outputs.mat;
load validation_targets.mat;


[C,order] = confusionmat(validationtargets,validationoutputs)