clear all;
close all;
clc;

load validation_outputs.mat;
load validation_targets.mat;
load targets.mat;


for(i=1:1122)
    
    validation_outputs_bin(validationoutputs(i),i)=1;
    
end


for(i=1:1122)
    
    validation_targets_bin(validationtargets(i),i)=1;
    
end



[C,order] = confusionmat(validationtargets,validationoutputs)

% imagesc(confusionmat(validationtargets,validationoutputs));
% colorbar;

plotconfusion(validation_outputs_bin,validation_targets_bin);

desiredoutputs = zeros(7,7854);


for(i=1:7854)
    
    desiredoutputs(targets(i),i)=1;
    
end
