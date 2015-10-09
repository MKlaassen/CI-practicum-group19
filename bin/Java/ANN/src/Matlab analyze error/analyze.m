clear all;
close all;
clc;

filenames{1} = 'results_7.txt';
filenames{2} = 'results_8.txt';
filenames{3} = 'results_9.txt';
filenames{4} = 'results_10.txt';
filenames{5} = 'results_11.txt';
filenames{6} = 'results_12.txt';
filenames{7} = 'results_13.txt';
filenames{8} = 'results_14.txt';
filenames{9} = 'results_15.txt';
filenames{10} = 'results_16.txt';
filenames{11} = 'results_17.txt';
filenames{12} = 'results_18.txt';
filenames{13} = 'results_19.txt';
filenames{14} = 'results_20.txt';
filenames{15} = 'results_21.txt';
filenames{16} = 'results_22.txt';
filenames{17} = 'results_23.txt';
filenames{18} = 'results_24.txt';
filenames{19} = 'results_25.txt';
filenames{20} = 'results_26.txt';
filenames{21} = 'results_27.txt';
filenames{22} = 'results_28.txt';
filenames{23} = 'results_29.txt';
filenames{24} = 'results_30.txt';

for h=1:24;


%% Import data from text file.

%% Initialize variables.
filename = filenames{h};
delimiter = ',';
startRow = [4,7,12,15,20,23,28,31,36,39];
endRow = [4,7,12,15,20,23,28,31,36,39];

%% Format string for each line of text:
formatSpec = '%s%*s%*s%*s%*s%*s%*s%*s%[^\n\r]';

%% Open the text file.
fileID = fopen(filename,'r');

%% Read columns of data according to format string.
dataArray = textscan(fileID, formatSpec, endRow(1)-startRow(1)+1, 'Delimiter', delimiter, 'HeaderLines', startRow(1)-1, 'ReturnOnError', false);
for block=2:length(startRow)
    frewind(fileID);
    dataArrayBlock = textscan(fileID, formatSpec, endRow(block)-startRow(block)+1, 'Delimiter', delimiter, 'HeaderLines', startRow(block)-1, 'ReturnOnError', false);
    dataArray{1} = [dataArray{1};dataArrayBlock{1}];
end

%% Close the text file.
fclose(fileID);

%% Create output variable
results = [dataArray{1:end-1}];
%% Clear temporary variables
clearvars filename delimiter startRow endRow formatSpec fileID block dataArrayBlock dataArray ans;
clearvars errorpercentage_test errorpercentage_validation remain token 
clearvars errorpercentage_test_string test_error_doubles
clearvars errorpercentage_validation_string validation_error_doubles

errorpercentage_test = results(1:2:end,:);

errorpercentage_validation = results(2:2:end,:);


for k=1:length(errorpercentage_test);
[token, remain] = strtok(errorpercentage_test(k));
[token, remain] = strtok(remain);
remain = remain{1,1}(3:end-3);
errorpercentage_test_string{k}=remain;

end

test_error_doubles = str2double(errorpercentage_test_string);
matrix_test_errors(:,h) = test_error_doubles;

for k=1:length(errorpercentage_validation);
[token, remain] = strtok(errorpercentage_validation(k));
[token, remain] = strtok(remain);
remain = remain{1,1}(3:end-3);
errorpercentage_validation_string{k}=remain;

end

validation_error_doubles = str2double(errorpercentage_validation_string);
matrix_validation_errors(:,h) = validation_error_doubles;


clearvars filename delimiter startRow endRow formatSpec fileID block dataArrayBlock dataArray ans;
clearvars errorpercentage_test errorpercentage_validation remain token 
clearvars errorpercentage_test_string test_error_doubles
clearvars errorpercentage_validation_string validation_error_doubles

end

%%calculate average of validation and test errors;
test_errors_mean = mean(matrix_test_errors);
validation_errors_mean = mean(matrix_validation_errors);

clearvars h k matrix_test_errors matrix_validation_errors filenames results


hiddenneurons = linspace(7,30,24);
subplot(211)
stem(hiddenneurons,test_errors_mean)
title('Error percentages of test fold')
ylabel('error %')
xlabel('number of hidden neurons')

subplot(212)
stem(hiddenneurons,validation_errors_mean)
title('Error percentages of validation fold')
ylabel('error %')
xlabel('number of hidden neurons')
